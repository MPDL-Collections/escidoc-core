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
package de.escidoc.core.adm.business.renderer.interfaces;

import java.util.HashMap;

import de.escidoc.core.common.exceptions.system.WebserverSystemException;

/**
 * Interface of a admin renderer.
 * 
 * @author MIH
 */
public interface AdminRendererInterface {

    /**
     * Gets the index-configuration as xml.
     * 
     * @param indexConfiguration
     *            The indexConfiguration to render.
     * @return Returns the XML representation of the indexConfiguration.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     */
    String renderIndexConfiguration(final HashMap<String, HashMap<String, 
            HashMap<String, Object>>> indexConfiguration) 
                                        throws WebserverSystemException;

}
