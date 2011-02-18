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

/**
 * Class encapsulating the information stored about the scope definition of an
 * {@link EscidocRole}.
 * 
 * @aa
 * @author TTE
 * 
 */
public class ScopeDef extends ScopeDefBase implements Comparable<ScopeDef> {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 1L;

    // CHECKSTYLE:JAVADOC-OFF

    /**
     * @see de.escidoc.core.aa.business.persistence.ScopeDefBase()
     */
    public ScopeDef() {
        super();
    }

    /**
     * @see de.escidoc.core.aa.business.persistence.ScopeDefBase(String, String,
     *      EscidocRole)
     */
    public ScopeDef(final String objectType, final String attributeId,
        final String attributeObjectType, final EscidocRole escidocRole) {
        super(objectType, attributeId, attributeObjectType, escidocRole);
    }

    /**
     * @see de.escidoc.core.aa.business.persistence.ScopeDefBase(String)
     */
    public ScopeDef(final String objectType) {
        super(objectType);
    }

    /**
     * See Interface for functional description.
     * 
     * @param o
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * @aa
     */
    public final int compareTo(final ScopeDef o) {

        if (o == null) {
            throw new NullPointerException("Parameter may not be null");
        }

        final String ownId = getId();
        final String oId = o.getId();

        if (ownId == null) {
            if (oId == null) {
                return 0;
            }
            else {
                return -1;
            }
        }
        else {
            if (oId == null) {
                return 1;
            }
            else {
                if (ownId.length() == oId.length()) {
                    return ownId.compareTo(oId);
                }
                else if (ownId.length() > oId.length()) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        }
    }

    /**
     * See Interface for functional description.
     * 
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     * @aa
     */
    @Override
    public final boolean equals(final Object obj) {

        boolean ret = false;
        if (obj instanceof ScopeDef) {
            ret = (compareTo((ScopeDef) obj) == 0);
        }
        return ret;
    }

    /**
     * See Interface for functional description.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     * @aa
     */
    @Override
    public final int hashCode() {

        return getId().hashCode();
    }

    // CHECKSTYLE:JAVADOC-ON

}
