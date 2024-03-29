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

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.FormattedName;
import ezvcard.property.StructuredName;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Marcel Berkholz <code@berkholz.org>
 *
 */
public class CommandOptions {

    Options options;
    CommandLine cmd;
    String outputFile;
    String inDirectory;
    String inFile;
    String phonebookName;
    String[] args;
    boolean skipEmptyContacts;
    boolean reversedOrder;
    boolean useUTF8Reader;
    private String filetype;

    /**
     *
     * @param args Command Line Parameters
     * @throws ParseException
     *
     */
    public CommandOptions(String[] args) throws ParseException {
        this.inFile = "";
        this.outputFile = "";
        this.inDirectory = "";
        // Default phonebook name is "Privat"
        this.phonebookName = "Privat";
        this.reversedOrder = false;
        this.skipEmptyContacts = false;
        this.useUTF8Reader = false;
        this.options = new Options();
        this.args = args;
        this.filetype = "vcf";
    }

    /**
     * Define the possible command line options.
     *
     */
    public void setOptions() {
        // options definitions
        this.options.addOption("h", "help", false, "Show help.");
        this.options.addOption("d", "directory", true,
                "Directory to search for vCards/CSVs. Every contact is given in a single vCard/CSV file.");
        this.options.addOption("f", "file", true, "Read all contacts from vCard/CSV file or from stdin.");
        this.options.addOption("o", "outfile", true, "Save XML output to file.");
        this.options.addOption("t", "filetype", true, "Specify the file format. Possible values are: csv, vcf. Default: vcf.");
        this.options.addOption("n", "phonebookname", true, "Rename phonebook to given name.");
        this.options.addOption("c", "create-template", false, "Create a template file for importing and exit. Used with Option -t.");
        this.options.addOption("r", "reversed-name-order", false,
                "Reverse the default order of the fullname. Default order: <surname> <name>.");

        this.options.addOption("s", "skip-empty-contacts", false, "Skip contacts with no mail address and no telephone numbers.");
        this.options.addOption("u", "use-utf8-reader ", false, "Set the vCard reader to UTF-8 character set");
        this.options.addOption("v", "verbose", false, "Be verbose. [NOT YET IMPLEMENTED.]");

        // instantiate parser with our options
        //CommandLineParser parser = new GnuParser();
        try {
            CommandLineParser parser = new DefaultParser();
            this.cmd = parser.parse(this.options, this.args);
        } catch (ParseException e) {
            System.out.println("Error while parsing the command line options. " + e.getLocalizedMessage() + "\n");
            Main.printHelp(options);
            System.exit(1);
        }
    }

    /**
     * Checks the options, if all recommended options are given and if excluding
     * parameters are met.
     */
    public void checkOptions() {
        if (cmd.hasOption("h")) {
            Main.printHelp(this.options);
            System.exit(0);
        }

        // creating the template file. 
        if (cmd.hasOption("c")) {
            generateTemplateFile();
            System.exit(0);
        }

        if (cmd.hasOption("o")) {
            this.outputFile = cmd.getOptionValue("o");
        }

        if (cmd.hasOption("t")) {
            try {
                if (cmd.getOptionValue("t").equalsIgnoreCase("")) {
                }
                this.filetype = cmd.getOptionValue("t");
            } catch (Exception ex) {
                System.out.println("Option -t has no value given.");
                Main.printHelp(this.options);
                System.exit(1);
            }
            // check if a supported file type is specified
            if (!"vcf".equals(filetype) && !"csv".equals(filetype)) {
                System.out.println("You have to specify a valid file type.\n");
                Main.printHelp(this.options);
                System.exit(1);
            } else {
                // check if option -d is given and filetype csv, this is not supported yet.
                if ("csv".equals(filetype) && cmd.hasOption("d")) {
                    System.out.println("Option -d in conjuction with filetype csv is not implemented yet. Please specify only a file or use vcard file format.\n");
                    Main.printHelp(this.options);
                    System.exit(1);
                }
            }
        } else {
            System.out.println("You have to specify the file type.\n");
            Main.printHelp(this.options);
            System.exit(1);
        }

        // check if both options (-f and -d) are given or not.
        if (!cmd.hasOption("d") & !cmd.hasOption("f")) {
            System.out.println("You have to specify either the -d or -f option.\n");
            Main.printHelp(options);
            System.exit(1);
        } else if (cmd.hasOption("d") & cmd.hasOption("f")) {
            System.out.println("You have to specify only one of the -d or -f options, not both.");
            System.exit(1);
        }

        // check if option -d is given and read in vcard from dir
        if (cmd.hasOption("d")) {
            inDirectory = cmd.getOptionValue("d");
            System.out.println("Reading vcard files from directory: "
                    + inDirectory);
        } else if (cmd.hasOption("f")) {
            inFile = cmd.getOptionValue("f");
        }

        if (cmd.hasOption("n")) {
            phonebookName = cmd.getOptionValue("n");
        }

        if (cmd.hasOption("s")) {
            skipEmptyContacts = true;
        }

        if (cmd.hasOption("r")) {
            reversedOrder = true;
        }
        
        if (cmd.hasOption("u")) {
            useUTF8Reader = true;
        }
    }

    private void generateTemplateFile() {
        // TODO: check if option f is given and take this filename instead
        try {
            Files.write(Paths.get("template." + cmd.getOptionValue("t")), generateTemplate(cmd.getOptionValue("t")).getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CommandOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String generateTemplate(String filetype) {
        if ("csv".equals(filetype)) {
            return generateCSVTemplate();
        } else if ("vcf".equals(filetype)) {
            return generateVcardTemplate();
        }
        return null;
    }

    private static String generateCSVTemplate() {
        return "givenname,familyname,home,work,mobile,fax,email\nJohn,Doe,0821/37097123,,,,john.doe@example.com";
    }

    private static String generateVcardTemplate() {
        VCard vcardExample = new VCard();

        vcardExample.addFormattedName(new FormattedName("John Doe"));
        vcardExample.addEmail("john.doe@example.com", EmailType.HOME);
        vcardExample.addTelephoneNumber("0821/37097123", TelephoneType.HOME);
        vcardExample.addTelephoneNumber("0821/37097122", TelephoneType.WORK);
        vcardExample.addTelephoneNumber("0171/37097121", TelephoneType.CELL);
        vcardExample.addTelephoneNumber("030/55512345", TelephoneType.FAX);
        StructuredName sn = new StructuredName();
        sn.setGiven("John");
        sn.setFamily("Doe");
        vcardExample.setStructuredName(sn);
        return Ezvcard.write(vcardExample).version(VCardVersion.V4_0).go();
    }
}
