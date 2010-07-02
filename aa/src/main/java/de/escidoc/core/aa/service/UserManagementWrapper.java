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
package de.escidoc.core.aa.service;

import de.escidoc.core.aa.service.interfaces.UserManagementWrapperInterface;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * The User management wrapper in service layer.
 * 
 * @spring.bean id="service.UserManagementWrapper"
 * @interface class="de.escidoc.core.aa.service.interfaces.UserManagementWrapperInterface"
 * @author TTE
 * @service
 * @aa
 */
public class UserManagementWrapper implements UserManagementWrapperInterface {

    private de.escidoc.core.aa.business.interfaces.UserManagementWrapperInterface business;

    // CHECKSTYLE:JAVADOC-OFF

    /**
     * See Interface for functional description.
     * 
     * @throws AuthenticationException
     * @throws SystemException
     * @see de.escidoc.core.aa.service.interfaces.UserManagementWrapperInterface
     *      #logout()
     * @aa
     */
    public void logout() throws AuthenticationException, SystemException {

        business.logout();
    }

    /**
     * See Interface for functional description.
     * 
     * @param handle the handle
     * @throws AuthenticationException
     *             Thrown if the authentication fails due to an invalid provided
     *             eSciDocUserHandle.
     * @throws SystemException
     *             Thrown in case of an internal error.
     * 
     * @aa
     */
    public void initHandleExpiryTimestamp(final String handle) 
        throws AuthenticationException, SystemException {
        business.initHandleExpiryTimestamp(handle);
    }
    // CHECKSTYLE:JAVADOC-ON

    /**
     * Setter for the business object.
     * 
     * @spring.property ref="business.UserManagementWrapper"
     * @param business
     *            business object.
     * @service.exclude
     * @aa
     */
    public void setBusiness(
        final de.escidoc.core.aa.business.interfaces.UserManagementWrapperInterface business) {

        this.business = business;
    }

}
