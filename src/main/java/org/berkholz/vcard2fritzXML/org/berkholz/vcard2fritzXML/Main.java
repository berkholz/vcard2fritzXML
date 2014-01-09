/**
 * 
 */
package org.berkholz.vcard2fritzXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// libraries for XML generation
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

// libraries for command line parsing
import org.apache.commons.cli.ParseException;

// libraries for vcard operations
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
	 */
	public static void printHelp() {
		System.out.println("Usages: ");
		System.out.println("vcard2fritzXML -f c:\\path\\to\\vcards\\all_vcards.vcard > fritz_phonebook.xml");
		System.out.println("vcard2fritzXML -d c:\\path\\to\\vcards\\ > fritz_phonebook.xml");
		System.out.println("vcard2fritzXML -d c:\\path\\to\\vcards\\ -o fritz_phonebook.xml\n");
		System.out.println("Possible command line options: ");
		System.out
				.println("\t -d <DIR_NAME> \t\t Directory to search for vcards. Every contact is given in a single vcard file. [NOT YET IMPLEMENTED.]");
		System.out.println("\t -f <FILE_NAME> \t Vcard file with all contacts in it.");
		System.out.println("\t -o <FILE_NAME> \t Save XML output to file <FILE_NAME>.");
		System.out.println("\t -n <PHONEBOOK_NAME> \t Rename phonebook to <PHONEBOOK_NAME>.");
		System.out.println("\t -v|--verbose \t\t Be verbose. [NOT YET IMPLEMENTED.]");
		System.out.println("\t -h|--help \t\t Show help.");
	}

	/**
	 * Find the mail address with the given type.
	 * 
	 * @param emailList
	 * @param mailType
	 * @return The first mail address found in the mailList.
	 */
	public static String getEmailAddress(List<EmailType> emailList, String mailType) {
		// TODO: funzt nicht
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
		// TODO: Unterschied zwischen work und home fax wird nicht erkannt
		for (TelephoneType tel : telephoneList) {
			if (tel.getTypes().toString().contains(telephoneType)) {
				return tel.getText();
			}
		}

		return new String();
	}

	/**
	 * Prints out the vcardfile to stdout.
	 * @param FileInputStream 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		// Read in a vCard
		try {
			File file = new File(cmdOptions.vcardFile);

			// print out the VCardFile if wanted
			// Main.printVCardFile(new FileInputStream(file));

			// get all vcard entries from file
			vcard = Ezvcard.parse(file).all();

			// iterate of any vcard entries
			Iterator<VCard> vcardIterator = vcard.iterator();

			while (vcardIterator.hasNext()) {
				int uidCounter = 1;
				// get next vcard entry
				VCard vcardElement = vcardIterator.next();

				// create new contact to represent the actual vcard element
				Contact c1 = new Contact();

				// set contact informations, like modtime, uid, name, mail,
				// phone etc.
				c1.setMod_time();
				c1.setUid(uidCounter++);
				c1.setServices(vcardElement.getEmails().get(0).getValue());

				// TODO: nach mehreren Begriffen suchen, wie cell, mobile etc.
				c1.setTelephony(new Telephony(
						Main.getTelephoneNumberByType(vcardElement.getTelephoneNumbers(), "home"), Main
								.getTelephoneNumberByType(vcardElement.getTelephoneNumbers(), "work"), Main
								.getTelephoneNumberByType(vcardElement.getTelephoneNumbers(), "cell")));

				c1.setPerson(new Person(vcardElement.getFormattedName().getValue()));

				// add contact to out phonebook element
				pb.addContact(c1);
			}
		} catch (Exception e) {
			System.out.println("Datei " + cmdOptions.vcardFile
					+ " konnte nicht geöffnet werden und schmiss folgenden Fehler: " + e.getStackTrace());
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
