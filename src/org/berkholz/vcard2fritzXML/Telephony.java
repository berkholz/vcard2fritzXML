package org.berkholz.vcard2fritzXML;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Child element of the contact element in the XML document. Contains the
 * numbers.
 * 
 * @author Marcel Berkholz
 */
public class Telephony {

	// XML attribute for Telephony
	@XmlAttribute
	Integer nid = 3;

	// List of numbers
	@XmlElement(name = "number")
	private ArrayList<Number> numbers;

	/**
	 * Constructor creates a telephony with initial three numbers: home, work
	 * and mobile.
	 * 
	 * @param numberHome
	 *            String representation of the home number.
	 * @param numberWork
	 *            String representation of the work number.
	 * @param numberMobile
	 *            String representation of the mobile number.
	 */
	public Telephony(String numberHome, String numberWork, String numberMobile) {
		// create list of Numbers
		this.numbers = new ArrayList<Number>();

		// create Number with Home-Number
		Number homeNumber = new Number(numberHome);
		homeNumber.setId(0); // set id of Home-Number
		homeNumber.setPrio(1); // set priority of Home-Number
		this.numbers.add(homeNumber); // add Number to list

		Number workNumber = new Number(numberWork);
		workNumber.setId(1); // set id of Home-Number
		workNumber.setPrio(3); // set priority of Home-Number
		this.numbers.add(workNumber); // add Number to list

		Number mobileNumber = new Number(numberMobile);
		mobileNumber.setId(2); // set id of Home-Number
		mobileNumber.setPrio(2); // set priority of Home-Number
		this.numbers.add(mobileNumber); // add Number to list

	}

	/**
	 * Get all telephone numbers as ArrayList<Number>
	 * 
	 * @return numbers List of telephone numbers as ArryList<Number>.
	 */
	public ArrayList<Number> getNumbers() {
		return this.numbers;
	}

	/**
	 * Set the number of specified number type. Sets the number only when the
	 * type exists once. If two number are from the same type nothing would be
	 * changed.
	 * 
	 * @param number
	 *            telephone number as String.
	 * @param numberType
	 *            Type of the number.
	 */
	public void setNumber(String number, NumberType numberType) {
		// set home number, when only one number of specified number type is
		// existent
		if (this.isNumberTypeAtomar(numberType)) {
			for (Number currentNumber : this.numbers) {
				if (currentNumber.getType() == numberType) {
					currentNumber.setNumber(number);
				}
			}
		}

	}

	/**
	 * Checks if the type of the number exists only once.
	 * 
	 * @param numberType
	 *            Type of the number.
	 * @return Returns true if type of number exists once, otherwise returns
	 *         false.
	 */

	public boolean isNumberTypeAtomar(NumberType numberType) {
		int occurences = 0;
		for (Number currentNumber : this.numbers)
			if (currentNumber.getType() == NumberType.home) {
				occurences++;
			}

		if (occurences <= 1)
			return true;
		else
			return false;
	}
}
