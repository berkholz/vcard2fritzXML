package org.berkholz.vcard2fritzXML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Root element for the XML document.
 * 
 * @author Marcel Berkholz
 *
 */

@XmlRootElement()
public class Phonebooks {

	@XmlElement(name = "phonebook")
	private Phonebook phonebook;

	/** 
	 * Instantiate the phonebooks.
	 */
	public Phonebooks(){
		super();
	}
	
	
	/**
	 * Get the phonebook.
	 * 
	 * @return The phonebook.
	 */
	public Phonebook getPhonebook() {
		return phonebook;
	}

	/**
	 * Set the phonebook.
	 * 
	 * @param phonebook Phonebook to set.
	 */
	public void setPhonebooks(Phonebook phonebook) {
		this.phonebook = phonebook;
	}

}
