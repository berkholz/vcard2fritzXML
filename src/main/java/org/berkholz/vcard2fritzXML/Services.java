/**
 * 
 */
package org.berkholz.vcard2fritzXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Marcel Berkholz
 * 
 */
//None of the fields or properties is bound to XML unless they are specifically
//annotated with some of the JAXB annotations.
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "services")
public class Services {

	@XmlElement(name = "email")
	private EMail email;

	@XmlAttribute
	Integer nid = 1;

	/**
	 * Instantiate the services.
	 */
	public Services() {
		this.email = new EMail();
	}

	/**
	 * Instantiate the services with mail address.
	 * 
	 * @param email Mail address for the services.
	 */
	public Services(String email) {
		this.email = new EMail(email);
	}

	/**
	 * Get the mail address.
	 * 
	 * @return Returns a valid mail address.
	 */
	public EMail getEmail() {
		return this.email;
	}

	/**
	 * Get the mail address.
	 * 
	 * @return Returns a valid mail address.
	 */
	public String getEmailAsString() {
		return this.email.getEmail();
	}
	
	/**
	 * Set the mail address.
	 * 
	 * @param email
	 *            Mail address as String to set..
	 */
	public void setEmail(EMail email) {
		// TODO: validate the mail address or is it done with new EMail()?
		this.email = email;
	}
}
