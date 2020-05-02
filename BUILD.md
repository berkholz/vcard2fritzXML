Here you find informations to build the tool by yourself.

# Requirements for building
For building the jar file you need the following software:
* Java OpenJDK 1.8 
  * (Package for Fedora: java-1.8.0-openjdk)
* Apache Maven 3.5.4 (http://maven.apache.org/)
  * (Packages for Fedora: maven, maven-jar-plugin)
* git 1.7 or higher (http://git-scm.com/)
  * (Package for Fedora: git)

# Cloning source code
For building the project you need the git repository: 

     git clone https://github.com/berkholz/vcard2fritzXML.git

# Compiling the code

     cd /path/to/git-repo/
     mvn clean package
 
# Executing the tool
See [README.md](https://github.com/berkholz/vcard2fritzXML/blob/master/README.md).
