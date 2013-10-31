package org.berkholz.vcard2fritzXML;

public class Person {
	
	private String realName;

	/**
	 * Instantiate a person with String as real name.
	 * 
	 * @param realName Real name of the Person.  
	 */
	public Person(String realName) {
		this.realName = realName;
	}
	
	/**
	 * Get the real name of the person.
	 * 
	 * @return The real name of the person. 
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Set the real name.
	 * 
	 * @param realName Real name of the person. 
	 */
	public void setRealName(String realName) {
		//TODO: validate the real name
		this.realName = realName;
	}
	
}
