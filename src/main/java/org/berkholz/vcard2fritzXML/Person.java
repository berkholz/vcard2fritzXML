package org.berkholz.vcard2fritzXML;

public class Person {

	private String realName;

	/**
	 * Instantiate an empty person.
	 * 
	 */
	public Person() {
		this.realName = new String();
	}

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
		this.realName = checkNameNull(realName);
	}

	/**
	 * Sets the realname of the Person object in the specified name order.
	 * Default name order is "givenname name". Reversed order is
	 * "name givenname". Names will be trimmed (spaces are deleted at beginning
	 * and end).
	 * 
	 * @param givenName The given name of the person.
	 * @param familyName The family name of the person.
	 * @param reversedNameOrder True, if the names should be stored in reverse order.
	 */
	public void setRealName(String givenName, String familyName, boolean reversedNameOrder) {
		if (reversedNameOrder) {
            givenName = checkNameNull(givenName);
            if(!givenName.isEmpty()) {
                realName = (checkNameNull(familyName) + ", " + givenName).trim();
            } else {
                realName = checkNameNull(familyName).trim();
            }
		} else {
			realName = (checkNameNull(givenName) + " " + checkNameNull(familyName)).trim();
		}
	}

	/**
	 * Helperfunction for checking a name for null value and setting it.
	 * 
	 * @param name The name to check if it is null or not.
	 * @return Returns the trimmed name if a value is present or an empty string
	 *         if name is null.
	 */
	private static String checkNameNull(String name) {
		if (name == null) {
			return "";
        }
		return name.trim();
	}
}
