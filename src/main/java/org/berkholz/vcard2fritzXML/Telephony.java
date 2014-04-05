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
	private final ArrayList<Number> numbers;

	/**
	 * Constructor creates a telephony with initial three numbers: home, work
	 * and mobile.
	 * 
	 * @param numberHome String representation of the home number.
	 * @param numberWork String representation of the work number.
	 * @param numberMobile String representation of the mobile number.
	 */
	public Telephony(String numberHome, String numberWork, String numberMobile) {
		// create list of Numbers
		this.numbers = new ArrayList<>();

		/* TODO: refactor the creation of this 3 numbers, same code...
		 * May be a new constructor would be the choice.
		 */
		
		// create home number
		Number homeNumber = new Number(numberHome);
		homeNumber.setId(0); // set id of home number
		homeNumber.setPrio(1); // set priority of home number
		homeNumber.setType(NumberType.home);
		this.numbers.add(homeNumber); // add number to list

        // create work number
		Number workNumber = new Number(numberWork);
		workNumber.setId(1); // set id of work number
        workNumber.setPrio(3); // set priority of work number
		workNumber.setType(NumberType.work);
		this.numbers.add(workNumber); // add number to list

        // create mobile number
		Number mobileNumber = new Number(numberMobile);
		mobileNumber.setId(2); // set id of mobile number
		mobileNumber.setPrio(2); // set priority of mobile number
		mobileNumber.setType(NumberType.mobile);
		this.numbers.add(mobileNumber); // add number to list
        
        // change number prio if numberHome is empty
        if(numberHome.isEmpty()) {
        	// TODO: Maybe the order should be defined by command line.
            mobileNumber.setPrio(1);
            workNumber.setPrio(2);
            homeNumber.setPrio(3);
        }
	}

	/**
	 * Get all telephone numbers as ArrayList.
	 * 
	 * @return List of telephone numbers as ArryList.
	 */
	public ArrayList<Number> getNumbers() {
		return this.numbers;
	}

	/**
	 * Set the type of the specified number. Sets the number only when the type
	 * exists once. If two number are from the same type nothing would be
	 * changed.
	 * 
	 * @param number telephone number as String.
	 * @param numberType Type of the number.
     * @return True, if number could be set, otherwise false.
	 */
	public Boolean setNumber(String number, NumberType numberType) {
		// set home number, when only one number of specified number type is
		// existent. FRITZ!Box allows two or more same types, but we check it.
		if (this.isNumberTypeAtomar(numberType)) {
			for (Number currentNumber : this.numbers) {
				// TODO: when adding of number fails, also true would be
				// returned.
				if (currentNumber.getType() == numberType) {
					currentNumber.setNumber(number);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds a number to the Telephony, so more than three numbers can be
	 * specified. Adds the number only when the type exists once. If two number
	 * are from the same type nothing would be changed.
	 * 
     * @param number
     * @param numberType
	 * @return True if number was added.
	 */
	public Boolean addNumber(String number, NumberType numberType) {
		if (this.isNumberTypeAtomar(numberType)) {
			// TODO: when adding of number fails, also true would be returned.
			this.numbers.add(new Number(number, numberType));
			return true;
		}
		return false;
	}

	/**
	 * Checks if the type of the number exists only once.
	 * 
	 * @param numberType Type of the number.
	 * @return Returns true if type of number exists once, otherwise returns false.
	 */
	public boolean isNumberTypeAtomar(NumberType numberType) {
		int occurences = 0;
		for (Number currentNumber : this.numbers)
			if (currentNumber.getType() == NumberType.home) {
				occurences++;
			}

        return occurences <= 1;
	}

	/**
	 * Checks if all telephone numbers are not set or empty.
	 * 
	 * @return Returns true when all telephone numbers are not set or empty.
	 */
	public boolean isEmpty() {
		// iterate over all numbers
		for (int i = 0; i < numbers.size(); i++) {
			// to avoid NullPointerExceptions we check if number objects are present
			if (numbers.get(i).getNumber() != null) {
				// one element need to be not empty
				if (!numbers.get(i).getNumber().isEmpty()) {
					return false;
				}
			}
		}
		// no numbers found
		return true;
	}
}
