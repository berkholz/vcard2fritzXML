/**
 * 
 */
package org.berkholz.vcard2fritzXML;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Marcel Berkholz <code@berkholz.org>
 * 
 */
public class CommandOptions {

	Options options;
	CommandLine cmd;
	String outputFile = "";
	String vcardDirectory = "";
	String vcardFile = "";
	String phonebookName = "Privat"; // Default phonebook name is "Privat"
	String[] args;
	boolean skipEmptyContacts = false;
	boolean reversedOrder = false;

	/**
	 * 
	 * @param String
	 *            [] args - Command line arguments.
	 * @throws ParseException
	 * 
	 */
	public CommandOptions(String[] args) throws ParseException {
		this.options = new Options();
		this.args = args;

	}

	/**
	 * Define the possible command line options.
	 * 
	 * @throws ParseException
	 */
	public void setOptions() throws ParseException {
		// options definitions
		this.options.addOption("h", "help", false, "Show help.");
		this.options.addOption("d", "directory", true,
				"Directory to search for vcards. Every contact is given in a single vcard file. [NOT YET IMPLEMENTED.]");
		this.options.addOption("f", "file", true, "Read all contacts from Vcard file or from stdin.");
		this.options.addOption("o", "outfile", true, "Save XML output to file.");
		this.options.addOption("n", "phonebookname", true, "Rename phonebook to given name.");
		this.options.addOption("r", "reversed-name-order", false,
				"Reverse the default order of the fullname. Default order: <surname> <name>.");
		this.options.addOption("s", "skip-empty-contacts", false, "Skip contacts with no mail address and no telephone numbers.");
		this.options.addOption("v", "verbose", false, "Be verbose. [NOT YET IMPLEMENTED.]");

		// instantiate parser with our options
		CommandLineParser parser = new GnuParser();
		this.cmd = parser.parse(this.options, this.args);
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

		if (cmd.hasOption("o")) {
			this.outputFile = cmd.getOptionValue("o");
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

		if (cmd.hasOption("d")) {
			System.out.println("The import of a directory within vcards is not yet implemented");
			System.exit(1);
			// TODO: Implement the import of vcards from a directory
			// vcardDirectory = cmd.getOptionValue("d");
			// System.out.println("Reading vcard files from directory: " +
			// vcardDirectory);
		}

		if (cmd.hasOption("f")) {
			vcardFile = cmd.getOptionValue("f");
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
	}
}
