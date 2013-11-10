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
 * Class for numbers in the address book. Every number has an id and a priority.
 * A number can be a type of NumberType and has a String representation of the
 * number. The number will be checked against RegExp.
 * 
 * @author Marcel Berkholz
 */

// None of the fields or properties is bound to XML unless they are specifically
// annotated with some of the JAXB annotations.
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "number")
public class Number {

	@XmlAttribute
	// ID of the number.
	private Integer id;

	@XmlAttribute
	private Integer prio;

	@XmlAttribute
	private NumberType type;

	// should only be regexp: [0-9/-.]
	@XmlValue
	private String number;

	/**
	 * Instantiate a number (constructor).
	 * 
	 * @param number Number as String representation.
	 */
	public Number(String number) {
		this.number = Number.validateNumber(number);
	}

	/**
	 * Instantiate a number (constructor).
	 * 
	 * @param number Number as String representation.
	 */
	public Number(String number, org.berkholz.vcard2fritzXML.NumberType numberType) {
		this.number = Number.validateNumber(number);
		if (this.number != null) {
			this.type = numberType;
		}
	}
	
	/**
	 * Returns the number of the contact.
	 * 
	 * @return The number as String.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Set the number and validate the format. If number has invalid characters
	 * null is assigned to number.
	 * 
	 * @param number Number as String to set.
	 */
	public void setNumber(String number) {
		// TODO: What happens when number is not valid. Should throw exception.
		this.number = validateNumber(number);
	}

	/**
	 * Checks if the string is a valid telephone number, that can contain
	 * "[0-9.\\/ -]+".
	 * 
	 * @return String Returns the number when it is valid otherwise null.
	 */
	public static String validateNumber(String number2validate) {
		if (number2validate.matches("\\+?[0-9.\\/ -]+")) {
			return number2validate;
		} else {
			return null;
		}
	}

	/**
	 * Check the number of valid format.
	 * 
	 * @return Returns true if number is valid otherwise false.
	 */
	public boolean isValidNumber() {
		if (this.number.matches("[0-9.\\/ -]+")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the id of the number.
	 * 
	 * @return Returns the id of the number.
	 * 
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id of the number
	 * 
	 * @param id The ID of the number to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the priority of the number in the telephony element.
	 * 
	 * @return Returns the priority of the number in the telephony element.
	 */
	public Integer getPrio() {
		return prio;
	}

	/**
	 * Sets the priority of the number.
	 * 
	 * @param prio
	 *            Priority of the number.
	 */
	public void setPrio(int prio) {
		this.prio = prio;
	}

	/**
	 * Return the type of the number.
	 * 
	 * @return Returns the type of the number.
	 */
	public NumberType getType() {
		return type;
	}

	/**
	 * Set the type of the number.
	 * 
	 * @param type
	 *            The type of the number to set.
	 */
	public void setType(NumberType type) {
		this.type = type;
	}

}
