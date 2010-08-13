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
package de.escidoc.core.test.aa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.escidoc.core.common.exceptions.remote.application.security.AuthorizationException;
import de.escidoc.core.test.security.client.PWCallback;

/**
 * Test suite for the role escidoc:role-context-administrator.
 * Context-Administrator may
 * -create contexts
 * -retrieve, modify, delete, open, close contexts (s)he created. 
 * 
 * This role is a unlimited role. (Has no scope-definitions).
 * 
 * @author MIH
 * 
 */
public class ContextAdminTest extends GrantTestBase {

    protected static final String HANDLE = PWCallback.TEST_HANDLE;

    protected static final String LOGINNAME = HANDLE;

    protected static final String PASSWORD = PWCallback.PASSWORD;

    protected static String grantCreationUserOrGroupId = null;
    
    private static int methodCounter = 0;
    
    /**
     * The constructor.
     * 
     * @param transport
     *            The transport identifier.
     * @param handlerCode
     *            handlerCode of either UserAccountHandler or UserGroupHandler.
     * @param userOrGroupId
     *            userOrGroupId for grantCreation.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public ContextAdminTest(
            final int transport, 
            final int handlerCode,
            final String userOrGroupId) throws Exception {
        super(transport, handlerCode);
        grantCreationUserOrGroupId = userOrGroupId;
    }

    /**
     * Set up servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Before
    public void initialize() throws Exception {
        if (methodCounter == 0) {
            //revoke all Grants
            revokeAllGrants(grantCreationUserOrGroupId);
            //create grant context-admin for user grantCreationUserOrGroupId 
            doTestCreateGrant(null, grantCreationUserOrGroupId, 
                null, ROLE_HREF_CONTEXT_ADMIN, null);
        }
    }

    /**
     * Clean up after servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @After
    public void deinitialize() throws Exception {
        methodCounter++;
        if (methodCounter == getTestAnnotationsCount()) {
            revokeAllGrants(grantCreationUserOrGroupId);
            methodCounter = 0;
        }
    }

    /**
     * Tests successfully creating a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testCreateContext() throws Exception {
        doTestCreateContext(
                HANDLE, 
                "context_create.xml", 
                null);
    }

    /**
     * Tests successfully retrieving a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRetrieveContext() throws Exception {
        doTestRetrieveContext(
                HANDLE, 
                HANDLE, 
                "context_create.xml", 
                null);
    }

    /**
     * Tests declining retrieving a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeclineRetrieveContext() throws Exception {
        doTestRetrieveContext(
                PWCallback.DEFAULT_HANDLE, 
                HANDLE, 
                "context_create.xml", 
                AuthorizationException.class);
    }

    /**
     * Tests successfully updating a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testUpdateContext() throws Exception {
        doTestUpdateContext(
                HANDLE, 
                HANDLE, 
                "context_create.xml", 
                null);
    }

    /**
     * Tests declining updating a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeclineUpdateContext() throws Exception {
        doTestUpdateContext(
                PWCallback.DEFAULT_HANDLE, 
                HANDLE, 
                "context_create.xml", 
                AuthorizationException.class);
    }

    /**
     * Tests successfully opening a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testOpenContext() throws Exception {
        doTestOpenContext(
                HANDLE, 
                HANDLE, 
                "context_create.xml", 
                null);
    }

    /**
     * Tests declining opening a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeclineOpenContext() throws Exception {
        doTestOpenContext(
                PWCallback.DEFAULT_HANDLE, 
                HANDLE, 
                "context_create.xml", 
                AuthorizationException.class);
    }

    /**
     * Tests successfully closing a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testCloseContext() throws Exception {
        doTestCloseContext(
                HANDLE, 
                HANDLE, 
                "context_create.xml", 
                null);
    }

    /**
     * Tests declining closing a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeclineCloseContext() throws Exception {
        doTestCloseContext(
                PWCallback.DEFAULT_HANDLE, 
                HANDLE, 
                "context_create.xml", 
                AuthorizationException.class);
    }

    /**
     * Tests successfully deleting a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeleteContext() throws Exception {
        doTestDeleteContext(
                HANDLE, 
                HANDLE, 
                "context_create.xml", 
                null);
    }

    /**
     * Tests declining deleting a context.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testDeclineDeleteContext() throws Exception {
        doTestDeleteContext(
                PWCallback.DEFAULT_HANDLE, 
                HANDLE, 
                "context_create.xml", 
                AuthorizationException.class);
    }

}
