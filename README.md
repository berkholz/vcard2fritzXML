These are the release notes for vcard2fritzXML.  Read them carefully,
as they tell you what this is all about, explain how to use vcard2fritzXML, 
and what to do if something goes wrong. 

# WHAT IS vcard2fritzXML?

vcard2fritzXML is litte Tool written in Java to convert a vcard file into an XML file 
with the format of a Fritz!Box addressbook import file.

	This Software is not tested and is in a pre alpha state. Don't use it until version 1.0 is released.


# SOFTWARE REQUIREMENTS

You need at least

 * git 1.7
 * Oracle Java 1.7
 * Maven 3.0.5

# ON WHAT HARDWARE DOES IT RUN?

Mostly on every platform with Oracle Java 7.

# DOWNLOAD AND BUILD

As long as there is no distribution, you must download and build the tool itself.

    git clone https://github.com/berkholz/vcard2fritzXML
    mvn clean install

# Usage

Use the shell script

    ./vcard2fritzXML.sh -h

convert a vcard directly with Maven

    mvn exec:java -Dexec.args="-h"

# DEVELOPMENT

Use the specific Maven goal to generate IDE projects file, e.g. for Eclipse

    mvn eclipse:eclipse

