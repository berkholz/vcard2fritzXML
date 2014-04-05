/**
 * 
 */
package org.berkholz.vcard2fritzXML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Child element of phonebooks. A phonebook contains contacts in the XML
 * document.
 * 
 * @author Marcel Berkholz
 * 
 */
public class Phonebook {
	@XmlAttribute
	private final String name;

	@XmlAttribute
	private final Integer owner;

	// add child element contact
	@XmlElement(name = "contact")
	private List<Contact> contacts;

	/**
	 * Instantiate a phonebook(contructor).
	 */
	public Phonebook() {
		this.contacts = new ArrayList<>();
		this.name = "Privat";
		this.owner = 1;
	}

	/**
	 * Instantiate a phonebook with phonebook name and owner.
	 * 
	 * @param phonebookName Name of the phonebook.
	 * @param phonebookOwner Owner of the phonebook.
	 */
	public Phonebook(String phonebookName, int phonebookOwner) {
		this.contacts = new ArrayList<>();
		this.name = phonebookName;
		this.owner = phonebookOwner;
	}

	/**
	 * Returns a list of contacts.
	 * 
	 * @return List of contacts.
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * 
	 * Substitute the list of contact with given list of contacts.
	 * 
	 * @param contacts
	 *            List of contacts.
	 */
	public void setPhonebook(List<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Add a contact to the phonebook.
	 * 
	 * @param contact
	 *            Contact to add to the phonebook.
	 */
	public void addContact(Contact contact) {
		if (!contacts.add(contact)) {
			// TODO: Add Exception here?
			System.out.print("Error while inserting a contact.");
		}
	}
}
