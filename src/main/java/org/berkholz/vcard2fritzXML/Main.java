/*
 * Copyright (C) 2014 Marcel Berkholz
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.types.EmailType;
import ezvcard.types.TelephoneType;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Marcel Berkholz
 *
 */
public class Main {

    /**
     * VARIABLES
     */
    List<VCard> vcard;
    
    List<CSVRecord> csv;

    // Options object including file and directory name
    protected CommandOptions cmdOptions;

    // the phonebooks element
    Phonebooks pbs;

    // create the phonebook element, will contain all of our contacts, and
    // assign it to our phonebooks element
    Phonebook pb;

// ************** CONSTRUCTOR
    /**
     * Constructor for Main class.
     *
     * @param args
     * @throws ParseException
     */
    public Main(String[] args) throws ParseException {

        // create Options object including file and directory name
        this.cmdOptions = new CommandOptions(args);

        // define the possible command line options
        cmdOptions.setOptions();

        // check if all options meet our requirements
        cmdOptions.checkOptions();

        // create the phonebooks element, contains a phonebook element
        pbs = new Phonebooks();

        //pb = new Phonebook(Main.cmdOptions.phonebookName, 1);
        pb = new Phonebook(cmdOptions.phonebookName, 1);
        
        pbs.setPhonebooks(pb);
    }

    /*
    FUNCTIONS
     */
    /**
     * Method for reading in the CSV entries from file, directory or stdin.
     * TODO: This method should be refactored with readInVCards() -> code
     * duplication
     */
    protected void readInCSVs() {
        // check if vcard input is given via stdin
        if (!"-".equals(this.cmdOptions.inFile)) {
            if (this.cmdOptions.inDirectory.isEmpty()) {
                // Read in a single csv
                try {
                    // get all csv entries from file
                    InputStreamReader inStreamReader = new InputStreamReader(new FileInputStream(this.cmdOptions.inFile));
                    CSVParser csvParser = new CSVParser(inStreamReader, CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim());
                    this.csv = csvParser.getRecords();
                } catch (IOException e) {
                    System.out.println("Error while opening file: " + this.cmdOptions.inFile + " StackTrace:\n" + Arrays.toString(e.getStackTrace()));
                }
            } else {
                // TODO: implement -d option for csv files
                System.out.println("NOT IMPLEMENTED YET!!");
                System.exit(1);
            }
        } else {
            // we get our vcard infos over stdin
            try {
                InputStreamReader inStreamReader = new InputStreamReader(System.in);
                
                CSVParser csvParser = new CSVParser(inStreamReader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());

                // get all vcard entries from stdin
                this.csv = csvParser.getRecords();
            } catch (IOException ioe) {
                System.out.println("Error while opening the InputStreamReader" + ioe.getLocalizedMessage());
            }
        }
    }

    /**
     * Method for reading in the vcards from file, directory or stdin.
     *
     * @throws IOException
     */
    protected void readInVCards() throws IOException {
        // check if vcard input is given via stdin
        if (!"-".equals(this.cmdOptions.inFile)) {
            if (this.cmdOptions.inDirectory.isEmpty()) {
                // Read in a single vCards
                try {
                    
                    File file = new File(this.cmdOptions.inFile);
                    // get all vcard entries from file
                    this.vcard = Ezvcard.parse(file).all();
                } catch (IOException e) {
                    System.out.println("Error while opening file: " + this.cmdOptions.inFile + " StackTrace:\n" + Arrays.toString(e.getStackTrace()));
                }
            } else {
                // check directory if it exists
                File dir = new File(this.cmdOptions.inDirectory);
                this.vcard = new ArrayList<>();
                if (dir.exists() && dir.isDirectory()) {
                    ArrayList<File> files = new ArrayList();
                    
                    for (File f : dir.listFiles(Main.getFileNameFilter("vcf"))) {
                        if (f.isFile()) {
//                            files.add(f);
                            List<VCard> tmpVcard = Ezvcard.parse(f).all();
                            this.vcard.addAll(tmpVcard);
                        }
                    }
                }
            }
        } else {
            // we get our vcard infos over stdin
            try {
                InputStreamReader inStreamReader = new InputStreamReader(System.in);
                // get all vcard entries from stdin
                this.vcard = Ezvcard.parse(inStreamReader).all();
            } catch (IOException ioe) {
                System.out.println("Error while opening the InputStreamReader" + ioe.getLocalizedMessage());
            }
        }
    }

    /**
     * Method to generate the contacts from csv entries.
     */
    protected void createContactsFromCSVs() {
        // iterate of any vcard entries
        Iterator<CSVRecord> csvIterator = csv.iterator();

        // initialize the uid counter for contacts
        int uidCounter = 1;
        
        while (csvIterator.hasNext()) {
            // get next vcard entry
            CSVRecord csvElement = csvIterator.next();

            // create new contact to represent the actual vcard element
            Contact c1 = new Contact();

            //CSV-HEADER: givenname, familyname, home, work, mobile, fax_work, email
            //String fax_work = csvElement.get("fax_work");
            c1.setServices(csvElement.get("email"));
            
            Telephony tp = new Telephony(csvElement.get("home"), csvElement.get("work"), csvElement.get("mobile"));
            
            c1.setTelephony(tp);

            // skip contact with no mail address and telephone numbers if
            // command line option -s is given
            if (cmdOptions.skipEmptyContacts && tp.isEmpty() && c1.getServices().getEmail().isEmpty()) {
                System.out.println("Skipping: " + csvElement.toString());
                continue;
            }
            
            Person p = new Person();
            p.setRealName(csvElement.get("givenname"), csvElement.get("familyname"), cmdOptions.reversedOrder);
            c1.setPerson(p);
            
            c1.setMod_time();
            c1.setUid(uidCounter++);

            // add contact to out phonebook element
            pb.addContact(c1);
        }
    }

    /**
     * Method to generate the contacts from vcard entries.
     */
    protected void createContactsFromVCards() {

        // iterate of any vcard entries
        Iterator<VCard> vcardIterator = vcard.iterator();

        // initialize the uid counter for contacts
        int uidCounter = 1;
        
        while (vcardIterator.hasNext()) {
            // get next vcard entry
            VCard vcardElement = vcardIterator.next();

            // create new contact to represent the actual vcard element
            Contact c1 = new Contact();
            
            String tmpmail = "";

            // if field mail is not empty take vcard entry
            if (!vcardElement.getEmails().isEmpty()) {
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

            // First use structured name, if not set use formatted name. 
            // In case both are empty and a company is set use this instead.
            if (vcardElement.getStructuredName() != null) {
                p.setRealName(vcardElement.getStructuredName().getGiven(), vcardElement.getStructuredName().getFamily(),
                        cmdOptions.reversedOrder);
            } else if (vcardElement.getFormattedName() != null) {
                p.setRealName(vcardElement.getFormattedName().getValue(), "", cmdOptions.reversedOrder);
            } else if (vcardElement.getOrganization() != null) {
                p.setRealName(vcardElement.getOrganization().getValues().get(0), "", cmdOptions.reversedOrder);
            } else {
                p.setRealName("");
            }
            
            c1.setPerson(p);
            
            c1.setMod_time();
            c1.setUid(uidCounter++);

            // add contact to out phonebook element
            pb.addContact(c1);
        }
    }

    /**
     * Marshalling the phonebook with all read entries from input source.
     *
     * @throws JAXBException
     */
    protected void marshall() throws JAXBException {
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

    /**
     * Just simply print a help.
     *
     * @param options
     */
    public static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("vcard2fritzXML", options);
        
        System.out.println("\nExamples: ");
        System.out.println("\tvcard2fritzXML -t vcf -f c:\\path\\to\\vcards\\all_vcards.vcard > fritz_phonebook.xml");
        System.out.println("\tcat test.vcf | vcard2fritzXML -t vcf -f - > fritz_phonebook.xml ");
        System.out.println("\tvcard2fritzXML -t vcf -d c:\\path\\to\\vcards\\ > fritz_phonebook.xml");
        System.out.println("\tvcard2fritzXML -t csv c:\\path\\to\\csv_file\\ -o fritz_phonebook.xml\n");
        
    }
    
    private static FilenameFilter getFileNameFilter(String extension) {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(extension);
            }
        };
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
            System.out.println("Error while printing the vcard file:\nStackTrace:\n" + Arrays.toString(e.getStackTrace()));
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

        // initialize our Main class as holder for our phonebooks, see constructor
        Main main = new Main(args);

        // check if vcard or csv is given
        switch (main.cmdOptions.cmd.getOptionValue("t")) {
            case "csv":
                // csv is given, read in all entries
                main.readInCSVs();
                main.createContactsFromCSVs();
                break;
            case "vcf":
                // vcard is given, read in all entries
                main.readInVCards();
                
                main.createContactsFromVCards();
                break;
            default:
                System.out.println("Something went wrong at file type determining. Exiting...");
                System.exit(2);
        }
        main.marshall();
    }
}
