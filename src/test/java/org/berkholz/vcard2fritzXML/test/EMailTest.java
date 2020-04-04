/*
 * Copyright (C) 2014 Marcel Berkholz
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.berkholz.vcard2fritzXML.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.berkholz.vcard2fritzXML.EMail;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for EMail.
 *
 * @author Marcel Berkholz
 *
 */
public class EMailTest {

    EMail mail1;
    EMail mail2;
    EMail mail3;
    EMail mail4;

    @Before
    public void setup() {
        mail1 = new EMail();
        mail2 = new EMail();
        mail3 = new EMail();
        mail4 = new EMail();
    }

    @Test
    public void setValidEMail() {
        mail1.setEmail("test@example.com");
        assertEquals("test@example.com", mail1.getEmail());
        
        mail2.setEmail("John Doe <john.doe@example.com>");
        assertEquals("john.doe@example.com", mail2.getEmail());
        
        mail3.setEmail("john.doe+folder@example.com");
        assertEquals("john.doe+folder@example.com", mail3.getEmail());
        
        mail4.setEmail("john_doe@example.com");
        assertEquals("john_doe@example.com", mail4.getEmail());
        
    }

    @Test
    public void testValidMail_username() {
        assertTrue("username@example.com is a valid mail address", EMail.validateEmail("test@example.com"));
    }

    @Test
    public void testValidMail_surname_name() {
        assertTrue("name.surname@example.com is a valid mail address", EMail.validateEmail("name.surname@example.com"));
    }

    @Test
    public void testValidMail_single_character() {
        assertTrue("a@example.de is a valid mail address", EMail.validateEmail("a@example.de"));
    }

    @Test
    public void testValidMail_numbers_characters_dot() {
        assertTrue("0815.billy@example.com is a valid mail address", EMail.validateEmail("0815.billy@example.com"));
    }

    @Test
    public void testInvalidMail_numbers_dot() {
        assertFalse("0815.@example.com is a valid mail address", EMail.validateEmail("0815.@example.com"));
    }

    @Test
    public void testInvalidMail_name_surrounded_with_dots() {
        assertFalse("Mail addresses may not begin with a dot.", EMail.validateEmail(".test.@examplecom"));
    }

    @Test
    public void testInvalidMail_with_plus() {
        assertFalse("Mail addresses may contain a + symbol.", EMail.validateEmail(".test.@examplecom"));
    }
    
    @Test
    public void testInvalidMail_dot_at_beginning() {
        assertFalse("Mail addresses may not begin with a dot.", EMail.validateEmail(".surname@example.com"));
    }
    
    @Test
    public void testInvalidMail_at_at_beginning(){
        assertFalse("Mail addresses may not begin with a @.", EMail.validateEmail("@surname@example.com"));
    }

    @Test
    /* 
    prior Apache common validator 1.4.1, 
    mails with umlauts are invalid. 
    With 1.4.1 they are valid, cause of RFC5322
     */
    public void testValidMail_umlauts() {
        EMail mail = new EMail("ää@example.de");
        assertTrue("Umlauts are not allowed in local-part.", EMail.validateEmail(mail.getEmail()));
    }
}
