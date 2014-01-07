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
