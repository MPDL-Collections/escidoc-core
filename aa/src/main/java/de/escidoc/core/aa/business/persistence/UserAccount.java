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

import de.escidoc.core.common.util.xml.XmlUtility;

import java.io.Serializable;
import java.util.Date;

/**
 * Class holding the user data.
 * 
 * @author TTE
 * 
 */
public class UserAccount extends UserAccountBase implements Serializable {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The default constructor.
     */
    public UserAccount() {
        super();
    }

    /**
     * @return Returns the href to this
     *         {@link de.escidoc.core.aa.business.persistence.UserAccount}
     *         object.
     */
    public final String getHref() {

        return XmlUtility.getUserAccountHref(getId());
    }

    public final void touch() {
        this.setLastModificationDate(new Date(System.currentTimeMillis()));
    }
}
