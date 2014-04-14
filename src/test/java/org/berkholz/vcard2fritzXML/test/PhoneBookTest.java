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

import java.util.List;

import org.berkholz.vcard2fritzXML.Contact;
import org.berkholz.vcard2fritzXML.Phonebook;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneBookTest {

    private Phonebook pb;

    @Before
    public void setup() {
        pb = new Phonebook();
    }

    @Test
    public void addContactToPhoneBook() {
        Contact contact = new Contact();
        contact.setUid(789);
        pb.addContact(contact);

        List<Contact> contacts = pb.getContacts();
        assertEquals(1, contacts.size());
        assertEquals(789, contacts.get(0).getUid());
    }

}
