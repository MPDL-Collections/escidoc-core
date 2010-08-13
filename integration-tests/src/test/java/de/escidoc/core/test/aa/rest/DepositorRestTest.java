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
package de.escidoc.core.test.aa.rest;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.escidoc.core.common.exceptions.remote.application.security.AuthorizationException;
import de.escidoc.core.test.aa.DepositorTest;
import de.escidoc.core.test.common.client.servlet.Constants;
import de.escidoc.core.test.security.client.PWCallback;

/**
 * Test suite for the role Depositor using the REST interface.
 * 
 * @author TTE
 * 
 */
@RunWith(Parameterized.class)
public class DepositorRestTest extends DepositorTest {

    /**
     * Initializes test-class with data.
     * 
     * @return Collection with data.
     * 
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {USER_ACCOUNT_HANDLER_CODE, 
                    PWCallback.ID_PREFIX + PWCallback.TEST_HANDLE},
                {USER_GROUP_HANDLER_CODE, USER_GROUP_WITH_GROUP_LIST_ID},
                {USER_GROUP_HANDLER_CODE, USER_GROUP_WITH_USER_LIST_ID},
                {USER_GROUP_HANDLER_CODE, USER_GROUP_WITH_OU_LIST_ID},
                {USER_GROUP_HANDLER_CODE, USER_GROUP_WITH_EXTERNAL_SELECTOR}
        });
    }

    /**
     * Constructor.
     * 
     * @param handlerCode handlerCode 
     *      of UserAccountHandler or UserGroupHandler
     * @param userOrGroupId
     *            userOrGroupId for grantCreation.
     * @throws Exception
     *             If anything fails.
     */
    public DepositorRestTest(final int handlerCode,
            final String userOrGroupId) throws Exception {

        super(Constants.TRANSPORT_REST, handlerCode, userOrGroupId);
    }

    /**
     * Tests successfully retrieving an content by a depositor.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRetrieveContent() throws Exception {

        doTestRetrieveContent(HANDLE, HANDLE, STATUS_PENDING,
            false, false, null, VISIBILITY_PUBLIC, null);
    }

    /**
     * Tests declining retrieving an content by a depositor.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeclineRetrieveContent() throws Exception {

        doTestRetrieveContent(HANDLE, PWCallback.DEFAULT_HANDLE, STATUS_PENDING,
            false, false, null, VISIBILITY_PUBLIC, AuthorizationException.class);
    }

}
