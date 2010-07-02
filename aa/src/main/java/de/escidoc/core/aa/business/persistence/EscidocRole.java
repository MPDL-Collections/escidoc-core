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
 * Copyright 2006-2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.aa.business.persistence;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sun.xacml.PolicySet;

import de.escidoc.core.aa.business.authorisation.CustomPolicyBuilder;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.string.StringUtility;
import de.escidoc.core.common.util.xml.XmlUtility;

/**
 * A role in eSciDoc.
 * 
 * @author TTE
 * @aa
 */
public class EscidocRole extends EscidocRoleBase {

    /**
     * The serial uid.
     */
    private static final long serialVersionUID = 1L;

    /** The name of the dummy role holding the default policies. */
    public static final String DEFAULT_USER_ROLE_ID = "escidoc:role-default-user";

    private transient PolicySet policySet;

    private List<String> objectTypes;

    private List<String> attributeObjectTypes;

    /**
     * Checks if this is a limited role or an unlimited role.
     * 
     * @return Returns <code>true</code> if this is a limited role,
     *         <code>false</code> else.
     * @aa
     */
    public boolean isLimited() {
        return getScopeDefs() != null && !getScopeDefs().isEmpty();
    }

    /**
     * Gets the XACML policy set representing this eSciDoc role.<br>
     * The policies of this role are returned in a
     * <code>XacmlRolePolicySet</code> with the policy combining algorithm set
     * to ordered-permit-overrides. The <code>PolicySet</code> objects are
     * cached.
     * 
     * @return Returns the <code>XacmlRolePolicySet</code> of this role.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @aa
     */
    public PolicySet getXacmlPolicySet() throws WebserverSystemException {

        if (policySet == null) {
            try {
                policySet = CustomPolicyBuilder.buildXacmlRolePolicySet(this);
            }
            catch (Exception e) {
                throw new WebserverSystemException(StringUtility
                    .concatenateWithBracketsToString(
                        "Error during getting of the role's policy set", e
                            .getMessage()), e);
            }
        }

        return policySet;
    }

    /**
     * Gets the policy set id (XACML).
     * 
     * @return Returns the policy set id of this role.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @aa
     */
    public URI getPolicySetId() throws WebserverSystemException {

        return getXacmlPolicySet().getId();
    }

    /**
     * Gets the objectTypes of the role's scope definitions.
     * 
     * @return Returns the objectTypes in a <code>List</code>.
     * @aa
     */
    public List<String> getObjectTypes() {

        objectTypes = null;
        if (objectTypes == null) {
            if (!isLimited()) {
                objectTypes = new ArrayList<String>(0);
            }
            else {
                Collection<ScopeDef> scopeDefs = getScopeDefs();
                objectTypes = new ArrayList<String>(scopeDefs.size());
                Iterator iter = scopeDefs.iterator();
                while (iter.hasNext()) {
                    ScopeDef scopeDef = (ScopeDef) iter.next();
                    objectTypes.add(scopeDef.getObjectType());
                }
            }
        }

        return objectTypes;
    }

    /**
     * Gets the AttributeObjectTypes of the role's scope definitions.
     * 
     * @return Returns the AttributeObjectTypes in a <code>List</code>.
     * @aa
     */
    public List<String> getAttributeObjectTypes() {

        attributeObjectTypes = null;
        if (attributeObjectTypes == null) {
            if (!isLimited()) {
                attributeObjectTypes = new ArrayList<String>(0);
            }
            else {
                Collection<ScopeDef> scopeDefs = getScopeDefs();
                attributeObjectTypes = new ArrayList<String>(scopeDefs.size());
                Iterator iter = scopeDefs.iterator();
                while (iter.hasNext()) {
                    ScopeDef scopeDef = (ScopeDef) iter.next();
                    attributeObjectTypes.add(scopeDef.getAttributeObjectType());
                }
            }
        }

        return objectTypes;
    }

    /**
     * Gets the href for this role.
     * 
     * @return Returns the href of this role.
     * @aa
     */
    public String getHref() {

        return XmlUtility.getRoleHref(this.getId());
    }

    // CHECKSTYLE:JAVADOC-OFF

    /**
     * See Interface for functional description.
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        ToStringBuilder toStringBuilder =
            new ToStringBuilder(this).append("roleName", getRoleName()).append(
                "id", getId());
        if (isLimited()) {
            toStringBuilder =
                toStringBuilder.append("# Limitation Conditions",
                    getScopeDefs().size());
        }
        return toStringBuilder.toString();
    }

    // CHECKSTYLE:JAVADOC-ON
}
