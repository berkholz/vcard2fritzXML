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

import org.berkholz.vcard2fritzXML.Person;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author berkholz
 */
public class PersonTest {

    Person p1Test;
    Person p2Test;

    public PersonTest() {
    }

    @Before
    public void setUp() {
        p1Test = new Person();
        p2Test = new Person("Marcel Berkholz");
    }

    @Test
    public void testEmptyCreatedPerson() {
        assertEquals("realname not equal", "", p1Test.getRealName());
    }

    @Test
    public void testEmptyCreatedPerson4Null() {
        assertNotNull("Check if empty user is created as null, shoiuld not be null.", p1Test);
    }

    @Test
    public void testSetPersonsRealname() {
        p1Test.setRealName("Max Mustermann");
        assertEquals("realname not equal after setting it", "Max Mustermann", p1Test.getRealName());
    }

    @Test
    public void testSetPersonsRealname_reversed() {
        p1Test.setRealName("Max", "Mustermann", true);
        assertEquals("realname not equal after setting it, reversed", "Mustermann, Max", p1Test.getRealName());
    }

    @Test
    public void testSetPersonsRealname_notreversed() {
        p1Test.setRealName("Max", "Mustermann", false);
        assertEquals("realname not equal after setting it, not reversed", "Max Mustermann", p1Test.getRealName());
    }
    
    @Test
    public void testSetPersonRealnameWithSpecialCharacters(){
        p1Test.setRealName("*+._öä!2@");
        assertEquals("Sonderzeichen scheinen nicht zu klappen.", "*+._öä!2@", p1Test.getRealName());
    }
}
