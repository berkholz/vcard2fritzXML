vcard2fritzXML is litte tool written in Java to convert a vcard or csv file into an XML file with the format of a Fritz!Box addressbook import file. 

# Which libraries are used?
To get to know which dependencies are actually used for this project, take a look at the [pom.xml](https://github.com/berkholz/vcard2fritzXML/blob/master/pom.xml). 

The following dependencies are used (status: 2020-04-30):
* com.googlecode.ez-vcard (version 0.8.5) [for managing vcard files]
* org.apache.commons.commons-lang3 (version 3.9)
* commons-cli [for command line parsing]
* commons-validator 
* junit (version 4.12) [for JUnit testing]
* org.apache.commons.commons-csv (version 1.7) [managing csv files]

# How to build or how to get the build?
You have the choice: 
- build the tool by yourself or 
- just downlod the build. 

The build ist downloadable in [releases topic](https://github.com/berkholz/vcard2fritzXML/releases) in the Code section. 
A brief introduction for building the tool can be found in the [BUILD.md](https://github.com/berkholz/vcard2fritzXML/BUILD.md).

# How to use?
For the process to import an address book into the Fritz!Box you have to:
* get your contacts into a vcard file or csv file.
* convert the vcard file into xml file with Fritz!Box format, specify an address book name
* Create an address book in the Fritz!Box with the specified name and import the xml file.

## Examples for usage

To call the help for vcard2fritzxml:

    java -jar /PATH/TO/git-repo/target/vcard2fritzXML-0.3.5-jar-with-dependencies.jar -h


To create a csv template file (template.csv) in the actual directory:

    java -jar /PATH/TO/git-repo/target/vcard2fritzXML-0.3.5-jar-with-dependencies.jar -t csv -c


To create a vcf template file (template.vcf) in the actual directory:

    java -jar /PATH/TO/git-repo/target/vcard2fritzXML-0.3.5-jar-with-dependencies.jar -t vcf -c


To convert a vcf file from standard out to the Fritz!Box xml format:

    cat template.vcf | java -jar vcard2fritzXML-0.3.5-jar-with-dependencies.jar -t vcf -f - 


To convert the vcard file to the Fritz!Box xml format and print out to standard out:

    java -jar /PATH/TO/git-repo/target/vcard2fritzXML-0.3.5-jar-with-dependencies.jar -t vcf -f "<PATH/TO/VCARDFILE.vcf>" 


To convert the vcard file to the Fritz!Box xml format and save it to file:

    java -jar vcard2fritzXML-0.3.5-jar-with-dependencies.jar -t vcf -f "<PATH/TO/VCARDFILE.vcf>" -o "<PATH/TO/OUTFILE.xml"


To convert the vcard file to the Fritz!Box xml format and set the address book name to "Syncronized":

    java -jar vcard2fritzXML-0.3.5-jar-with-dependencies.jar -t vcf -f "<PATH/TO/VCARDFILE.vcf>" -n "Syncronized"


## Import into Fritz!Box
Go to your Fritz!Box and create a new Telphonebook named "Addressbook" or as set via command line and recover your Telephonebook with the XML file.


# Tested 
Building the software is tested on the folowing setups:
* Linux (Fedora 30) with OpenJDK 1.8, Maven 3.4.5 

Importing the xml export is tested on:
* FritzOS 07.19-77061 BETA 


# Contributors
Special thanks to the following persons, who helped making this software better:
* Danny Gräf
* Ludger Kluitmann
