/*
  * CDDL HEADER START
  *
  * The contents of this file are subject to the terms of the
  * Common Development and Distribution License, Version 1.0 only
  * (the "License").  You may not use this file except in compliance
  * with the License.
  *
  * You can obtain a copy of the license at license/ESCIDOC.LICENSE
  * or http://www.escidoc.de/license.
  * See the License for the specific language governing permissions
  * and limitations under the License.
  *
  * When distributing Covered Code, include this CDDL HEADER in each
  * file and include the License file at license/ESCIDOC.LICENSE.
  * If applicable, add the following below this CDDL HEADER, with the
  * fields enclosed by brackets "[]" replaced with your own identifying
  * information: Portions Copyright [yyyy] [name of copyright owner]
  *
  * CDDL HEADER END
  */

/*
  * Copyright 2009 Fachinformationszentrum Karlsruhe Gesellschaft
  * fuer wissenschaftlich-technische Information mbH and Max-Planck-
  * Gesellschaft zur Foerderung der Wissenschaft e.V.
  * All rights reserved.  Use is subject to license terms.
  */


package de.escidoc.core.aa.ldap;

import org.springframework.security.userdetails.ldap.LdapUserDetailsImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object that holds LDAP-Userdata and all attributes from LDAP.
 * 
 * @author MIH
 * @aa
 */
public class EscidocLdapUserDetails extends LdapUserDetailsImpl {
    
    private static final long serialVersionUID = -3856754429168330690L;

    private final Map<String, List<String>> stringAttributes =
                                new HashMap<String, List<String>>();
    
    private String dn = null;
    
    private String username = null;

    /**
     * Adds an attribute to the HashMap of stringAttributes.
     * 
     * @param name name of attribute
     * @param value value of attribute
     * 
     * @aa
     */
    public final void addStringAttribute(final String name, final String value) {
        if (stringAttributes.get(name) == null) {
            stringAttributes.put(name, new ArrayList<String>());
        }
        stringAttributes.get(name).add(value);
    }

    /**
     * @return the attributes
     */
    public final Map<String, List<String>> getStringAttributes() {
        return stringAttributes;
    }
    
    /**
     * Returns the value of the attribute with the given name.
     * 
     * @param name name of the attribute.
     * @return one attribute with given name
     */
    public List<String> getStringAttribute(final String name) {
        return stringAttributes.get(name);
    }
    
    /**
     * @return the dn
     */
    @Override
    public final String getDn() {
        return dn;
    }

    /**
     * @param dn the dn to set
     */
    public final void setDn(final String dn) {
        this.dn = dn;
    }

    /**
     * @return the username
     */
    @Override
    public final String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

}
