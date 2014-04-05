/**
 * 
 */
package org.berkholz.vcard2fritzXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.types.EmailType;
import ezvcard.types.TelephoneType;

/**
 * @author Marcel Berkholz
 * 
 */
public class Main {

	/**
	 * Just simply print a help.
     * @param options
	 */
	public static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("vcard2fritzXML", options);

		System.out.println("\nExamples: ");
		System.out.println("\tvcard2fritzXML -f c:\\path\\to\\vcards\\all_vcards.vcard > fritz_phonebook.xml");
		System.out.println("\tcat test.vcf | vcard2fritzXML -f - > fritz_phonebook.xml ");
		System.out.println("\tvcard2fritzXML -d c:\\path\\to\\vcards\\ > fritz_phonebook.xml");
		System.out.println("\tvcard2fritzXML -d c:\\path\\to\\vcards\\ -o fritz_phonebook.xml\n");

	}

	/**
	 * Find the mail address with the given type.
	 * 
	 * @param emailList
	 * @param mailType
	 * @return The first mail address found in the mailList.
	 */
	public static String getEmailAddress(List<EmailType> emailList, String mailType) {
		// TODO: funzt nicht, wird zur Zeit nicht verwendet
		for (EmailType mail : emailList) {
			// System.out.println(mail.getValue());
			// System.out.println(mail.getTypes());
			// TODO: Vergleich geht auf die Adresse des Objektes und nicht auf
			// den String
			if (mail.getTypes().toString() == "[TYPE=" + mailType) {
				System.out.println(mail.getValue());
				// return mail.getValue();
			}
		}
		return null;
	}

	/**
	 * Find the telephone number with the given type.
	 * 
	 * @param telephoneList
	 * @param telephoneType
	 * @return The first telephone number found in the telephoneList.
	 */
	public static String getTelephoneNumberByType(List<TelephoneType> telephoneList, String telephoneType) {
		// TODO: difference between work fax and home fax is not recognized
		for (TelephoneType tel : telephoneList) {
			if (tel.getTypes().toString().contains(telephoneType)) {
				return tel.getText();
			}
		}

		return new String();
	}

	/**
	 * Prints out the vcardfile to stdout.
	 * 
	 * @param fis VCard file as FileInputStream.
	 */
	public static void printVCardFile(FileInputStream fis) {

		int oneByte;
		try {
			while ((oneByte = fis.read()) != -1) {
				System.out.write(oneByte);
				// System.out.print((char)oneByte); // could also do this
			}
			fis.close();
		} catch (IOException e) {
			System.out.println("Error while printing the vcard file:\nStackTrace:\n" + e.getStackTrace());
		}
		System.out.flush();
		System.out.println();
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws JAXBException
	 * @throws ParseException
	 * 
	 */
	public static void main(String[] args) throws IOException, JAXBException, ParseException {

		// Variables for command option parsing
		List<VCard> vcard = null;

		// create Options object including file and directory name
		CommandOptions cmdOptions = new CommandOptions(args);

		// define the possible command line options
		cmdOptions.setOptions();

		// check if all options meet our requirements
		cmdOptions.checkOptions();

		// create the phonebooks element, contains a phonebook element
		Phonebooks pbs = new Phonebooks();

		// create the phonebook element, will contain all of our contacts, and
		// assign it to our phonebooks element
		Phonebook pb = new Phonebook(cmdOptions.phonebookName, 1);
		pbs.setPhonebooks(pb);

		// check if vcard input is given via stdin
		if (! "-".equals(cmdOptions.vcardFile)) {
			// Read in a vCards
			try {

				File file = new File(cmdOptions.vcardFile);
				// get all vcard entries from file
				vcard = Ezvcard.parse(file).all();
			} catch (IOException e) {
				System.out.println("Error while opening file: " + cmdOptions.vcardFile + " StackTrace:\n" + e.getStackTrace());
			}
		} else {
			try {
				InputStreamReader inStreamReader = new InputStreamReader(System.in);
				// get all vcard entries from stdin
				vcard = Ezvcard.parse(inStreamReader).all();
			} catch (IOException ioe) {
				System.out.println("Error while opening the InputStreamReader" + ioe.getLocalizedMessage());
			}
		}

		// iterate of any vcard entries
		Iterator<VCard> vcardIterator = vcard.iterator();

		// initialize the uid counter for contacts
		int uidCounter = 1;

		while (vcardIterator.hasNext()) {
			// get next vcard entry
			VCard vcardElement = vcardIterator.next();

			// create new contact to represent the actual vcard element
			Contact c1 = new Contact();

			String tmpmail = new String();

			if (vcardElement.getEmails().isEmpty()) {
				tmpmail = "";
			} else {
				tmpmail = vcardElement.getEmails().get(0).getValue();
			}
			c1.setServices(tmpmail);

			// TODO: nach mehreren Begriffen suchen, wie cell, mobile etc.
			Telephony tp = new Telephony(Main.getTelephoneNumberByType(vcardElement.getTelephoneNumbers(), "home"),
					Main.getTelephoneNumberByType(vcardElement.getTelephoneNumbers(), "work"), Main.getTelephoneNumberByType(
							vcardElement.getTelephoneNumbers(), "cell"));

			c1.setTelephony(tp);

			// skip contact with no mail address and telephone numbers if
			// command line option -s is given
			if (cmdOptions.skipEmptyContacts && tp.isEmpty() && c1.getServices().getEmail().isEmpty()) {
				System.out.println("Skipping: " + vcardElement.getFormattedName().getValue());
				continue;
			}

			Person p = new Person();
			p.setRealName(vcardElement.getStructuredName().getGiven(), vcardElement.getStructuredName().getFamily(),
					cmdOptions.reversedOrder);
			c1.setPerson(p);

			c1.setMod_time();
			c1.setUid(uidCounter++);

			// add contact to out phonebook element
			pb.addContact(c1);
			c1 = null;
		}

		JAXBContext context = JAXBContext.newInstance(Phonebooks.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// output should be directed to stdout or file if Option -o is given
		if (cmdOptions.outputFile.isEmpty()) {
			m.marshal(pbs, System.out);
		} else {
			m.marshal(pbs, new File(cmdOptions.outputFile));
		}
	}
}
