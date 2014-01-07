package org.berkholz.vcard2fritzXML.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.validator.EmailValidator;
import org.berkholz.vcard2fritzXML.EMail;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcel Berkholz
 * 
 */
public class EMailTest {
	EMail mail;

	@Before
	public void setup() {
		mail = new EMail();
	}

	@Test
	public void setValidEMail() {
		mail.setEmail("test@example.com");
		assertEquals("test@example.com", mail.getEmail());
	}

	@Test
	public void testValidMail_username() {
		assertTrue("username@example.com is a valid mail address",
				EMail.validateEmail("test@example.com"));
	}

	@Test
	public void testValidMail_surname_name() {
		assertTrue("name.surname@example.com is a valid mail address",
				EMail.validateEmail("name.surname@example.com"));
	}

	@Test
	public void testValidMail_single_character() {
		assertTrue("a@example.de is a valid mail address",
				EMail.validateEmail("a@example.de"));
	}

	@Test
	public void testValidMail_numbers_characters_dot() {
		assertTrue("0815.billy@example.com is a valid mail address",
				EMail.validateEmail("0815.billy@example.com"));
	}

	@Test
	public void testValidMail_numbers_dot() {
		assertTrue("0815.@example.com is a valid mail address",
				EMail.validateEmail("0815.billy@example.com"));
	}

	@Test
	public void testInvalidMail_name_surrounded_with_dots() {
		assertFalse(".test.@examplecom is a invalid mail address",
				EMail.validateEmail("test@examplecom"));
	}

	@Test
	public void testInvalidMail_dot_at_beginning() {
		assertFalse(".surname@example.com is a invalid mail address",
				EMail.validateEmail(".surname@example.com"));
	}

	@Test
	public void testInvalidMail_umlauts() {
		assertFalse("הה@example.de is a invalid mail address", EMail.validateEmail("הה@example.de"));
	}
}
