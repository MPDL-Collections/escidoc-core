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
package de.escidoc.core.aa.business.xacml;

import com.sun.xacml.Indenter;
import com.sun.xacml.PolicyReference;
import com.sun.xacml.finder.PolicyFinder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Class holding the data of a policy reference.
 * 
 * @author TTE
 * @aa
 */
public class XacmlPolicyReference extends PolicyReference {

    /**
     * The constructor.
     * 
     * @param referenceId
     *            The id of the referenced policy or policy set.
     * @param type
     *            The type of the reference as specified in
     *            <code>PolicyReference</code>.
     * @param finder
     *            The policy finder to use for resolving the reference.
     */
    public XacmlPolicyReference(final URI referenceId, final int type,
        final PolicyFinder finder) {

        super(referenceId, type, finder);
    }


    /**
     * See Interface for functional description.
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    public final String toString() {

        final ByteArrayOutputStream writer = new ByteArrayOutputStream();
        encode(writer, new Indenter());
        final String ret = writer.toString();
        try {
            writer.close();
        }
        catch (IOException e) {
            // Ignore exception
        }
        return ret;
    }

}
