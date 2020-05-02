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
package org.berkholz.vcard2fritzXML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Root element for the XML document.
 *
 * @author Marcel Berkholz
 *
 */
@XmlRootElement()
public class Phonebooks {

    @XmlElement(name = "phonebook")
    private Phonebook phonebook;

    /**
     * Instantiate the phonebooks.
     */
    public Phonebooks() {
        super();
    }

    /**
     * Get the phonebook.
     *
     * @return The phonebook.
     */
    public Phonebook getPhonebook() {
        return phonebook;
    }

    /**
     * Set the phonebook.
     *
     * @param phonebook Phonebook to set.
     */
    public void setPhonebooks(Phonebook phonebook) {
        this.phonebook = phonebook;
    }

}
