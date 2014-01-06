/**
 * 
 */
package org.berkholz.vcard2fritzXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Mail address of the contact.
 * 
 * @author Marcel Berkholz
 * 
 */

/*
 * None of the fields or properties is bound to XML unless they are specifically
 * annotated with some of the JAXB annotations.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "email")
public class EMail {
	@XmlAttribute
	private Integer id;

	@XmlAttribute
	private String classifier;

	@XmlValue
	private String email;

	/**
	 * Instantiate an email address (constructor).
	 */
	public EMail() {
		this.email = new String();
		this.classifier = "private";
		// there is only one mail address possible
		this.id = 0;
	}

	/**
	 * Instantiate an email address with the given email address(constructor).
	 * 
	 * @param email
	 *            Mail address to instantiate with.
	 */
	public EMail(String email) {
		this.email = new String();
		this.setEmail(email);
		this.classifier = "private";
		// there is only one mail address possible
		this.id = 0;
	}

	/**
	 * Get the mail address.
	 * 
	 * @return The mail address as String.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the mail address.
	 * 
	 * @param email
	 *            mail address as String representation.
	 */
	public void setEmail(String email) {
		// TODO: validate the mail address
		this.email = email;
	}

	/**
	 * Validate a mail address.
	 * 
	 * @param email
	 *            Mail address as String representation.
	 * @return true if mail address is valid otherwise false
	 */
	protected boolean validateEmail(String email) {
		return false;
	}
}
