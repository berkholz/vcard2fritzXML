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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class for the Services XML element in the address book of the fritz box
 * import file.
 *
 * @author Marcel Berkholz
 *
 */
// None of the fields or properties is bound to XML unless they are specifically
// annotated with some of the JAXB annotations.
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "services")
public class Services {

    @XmlElement(name = "email")
    // mail address of a contact
    private EMail email;

    @XmlAttribute
    Integer nid = 1;

    /**
     * Instantiate the services.
     */
    public Services() {
        this.email = new EMail();
    }

    /**
     * Instantiate the services with mail address.
     *
     * @param email Mail address for the services.
     */
    public Services(String email) {
        try {
            this.email = new EMail(email);
        } catch (Exception e) {
            System.out.println("No valid email address given:" + e.getLocalizedMessage());
        }
    }

    /**
     * Get the mail address.
     *
     * @return Returns a valid mail address.
     */
    public EMail getEmail() {
        return this.email;
    }

    /**
     * Get the mail address.
     *
     * @return Returns a valid mail address as String.
     */
    public String getEmailAsString() {
        return this.email.getEmail();
    }

    /**
     * Set the mail address. Mail address will be validated and throws an
     * Exception if invalid.
     *
     * @param email Set the mail address to the given mail address.
     * @throws Exception
     */
    public void setEmail(EMail email) throws Exception {
        if (EMail.validateEmail(email.getEmail())) {
            this.email = email;
        } else {
            // TODO: Remove exception and do some better staff, like logger or dummy mail for not failing the complete import.  
            throw new Exception("No valid mail address given. Could not assign the mail address " + email.getEmail()
                    + " as child element to the XML element Services.");
        }
    }
}
