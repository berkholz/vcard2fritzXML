package org.berkholz.vcard2fritzXML;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * Child element for the XML document.
 * 
 * @author Marcel Berkholz
 * 
 */

// None of the fields or properties is bound to XML unless they are specifically
// annotated with some of the JAXB annotations.
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "contact")
public class Contact {
	@XmlElement(name = "category")
	private int category;

	@XmlElement(name = "person")
	private Person person;

	@XmlElement(name = "telephony")
	private Telephony telephony;

	@XmlElement(name = "services")
	private Services services;

	@XmlElement(name = "setup")
	private Setup setup;

	@XmlElement(name = "mod_time")
	private long mod_time;

	@XmlElement(name = "uniqueid")
	private int uid;

	/**
	 * Instantiate a contact (constructor).
	 */
	public Contact() {
		this.category = 0;
		this.setup = new Setup();
	}

	/**
	 * Checks if telephone numbers and mail address of the contact are empty.
	 * 
	 * @return Returns true if all telephone numbers and the mail address of the
	 *         contact are not set or null. Otherwise false.
	 */
	public boolean isEmpty() {
		if (this.services.getEmail().isEmpty() && this.telephony.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the category of the contact.
	 * 
	 * @return The category of the contact.
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * Get the person of the contact.
	 * 
	 * @return The person of the contact.
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Set the person of the contact.
	 * 
	 * @param person
	 *            Person object to set.
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Get the telephony of the contact.
	 * 
	 * @return The telephony of the contact.
	 */
	public Telephony getTelephony() {
		return telephony;
	}

	/**
	 * Set the telephony of the contact.
	 * 
	 * @param telephony
	 *            The telephony of the contact to set.
	 */
	public void setTelephony(Telephony telephony) {
		this.telephony = telephony;
	}

	/**
	 * Get the services of the contact.
	 * 
	 * @return The services of the contact.
	 */
	public Services getServices() {
		return services;
	}

	/**
	 * Set the services of the contact.
	 * 
	 * @param services
	 */
	public void setServices(Services services) {
		this.services = services;
	}

	/**
	 * Set the email address of the contact.
	 * 
	 * @param email
	 *            the email address of the contact.
	 */
	public void setServices(String email) {
		this.services = new Services(email);
	}

	/**
	 * Get the setup of the contact.
	 * 
	 * @return The setup of the contact.
	 */
	public Setup getSetup() {
		return setup;
	}

	/**
	 * Get the modification time of the contact as timestamp.
	 * 
	 * @return The modification time of the contact.
	 */
	public long getMod_time() {
		return this.mod_time;
	}

	/**
	 * Set the modification date to the actual timestamp.
	 * 
	 */
	public void setMod_time() {
		Calendar calendar = Calendar.getInstance();
		this.mod_time = calendar.getTimeInMillis() / 1000;
	}

	/**
	 * Set the modification date to the given timestamp.
	 * 
	 * @param mod_time
	 *            Modification time as timestamp.
	 */
	public void setMod_time(long mod_time) {
		this.mod_time = mod_time;
	}

	/**
	 * Get the unique ID of the contact.
	 * 
	 * @return The unique ID of the contact.
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Set the unique id of the contact.
	 * 
	 * @param uid
	 *            Unique ID of the contact.
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
}
