These are the release notes for vcard2fritzXML.  Read them carefully,
as they tell you what this is all about, explain how to use vcard2fritzXML, 
and what to do if something goes wrong. 

# WHAT IS vcard2fritzXML?

vcard2fritzXML is litte tool written in Java to convert a vcard file into an XML file 
with the format of a Fritz!Box addressbook import file.

# TESTED

vcard2fritzXML is tested on the following environments:
* Mac OS X 10.8.5, Maven 3.0.4, Oracle Java 1.7.0_45, FRITZ!OS 05.53
* Windows 8.1, Maven 3.1.1, Oracle Java 1.7.0_45, FRITZ!OS 06.01

# SOFTWARE REQUIREMENTS

You need at least
 * git 1.7
 * Oracle Java 1.7
 * Maven 3.0.4


# DOWNLOAD

As long as there is no distribution, you must download and build the tool itself.

    git clone https://github.com/berkholz/vcard2fritzXML
    
    
# BUILD

To compile and generate JAR file with and without dependencies call:

	mvn clean package


# COMMANDLINE USAGE

It depends on the JAR file.

## JAR file with no dependencies

Use the shell script:

    ./vcard2fritzXML.sh -h

Convert a vcard directly with Maven:

    mvn exec:java -Dexec.args="-f <PATH/TO/VCARDFILE.vcf>"


## JAR file with dependencies

To call vcard2fritzXML help:
    
    java -jar /PATH/TO/vcard2fritzXML_HOME/target/vcard2fritzXML-1.0-SNAPSHOT-jar-with-dependencies.jar -h


# USAGE
To import a vcard to your Fritz!Box you have to generate a valid vcard. 
On an Mac you can use the Addressbook: 
* Open Addressbook 
* Go to the group or contact 
* Choose option "export vcard..."
* Save the file to a location where you can find it on a commandline, mostly everywhere ;)

Now you can convert the vcard file to the Fritz!Box xml file:

    java -jar /PATH/TO/vcard2fritzXML-1.0-SNAPSHOT-jar-with-dependencies.jar -f "<PATH/TO/VCARDFILE.vcf>" -o "<PATH/TO/FRITZ!BOX_XML_FILE.xml>" -n "Addressbook"

Go to your Fritz!Box and create a new Telphonebook named "Addressbook" and recover your Telephonebook with the XML file.


# DEVELOPMENT

Use the specific Maven goal to generate IDE projects file, e.g. for Eclipse

    mvn eclipse:eclipse

