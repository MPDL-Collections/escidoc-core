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
 * Copyright 2007-2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.common.util.xml.factory;

import de.escidoc.core.common.exceptions.system.WebserverSystemException;

import java.util.Map;

public class ItemFoXmlProvider extends InfrastructureFoXmlProvider {

    private static final String ITEM_PATH = "/item";

    private static final String COMPONENT_PATH = "/component";

    private static final String ITEM_RESOURCE_NAME = "item";

    private static final String COMPONENT_RESOURCE_NAME = "component";

    private static final String CONTENT_RESOURCE_NAME = "content";

    private static final String DEFAULT_DC_RESOURCE_NAME =
        "component-dc-default";

    private static final String ITEM_RELS_EXT_PATH = ITEM_PATH;

    private static final String COMPONENT_RELS_EXT_PATH = COMPONENT_PATH;

    private static final String RELS_EXT_RESOURCE_NAME = "rels-ext";

    private static final String WOV_RESOURCE_NAME = "dummy-wov";

    private static final ItemFoXmlProvider PROVIDER = new ItemFoXmlProvider();

    /**
     * Private constructor to prevent initialization.
     * 
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @om
     */
    private ItemFoXmlProvider() {
    }

    /**
     * Gets the Item FoXML PROVIDER.
     * 
     * @return Returns the <code>ItemFoXmlProvider</code> object.
     * @throws WebserverSystemException
     *             Thrown if the instance cannot be created due to an internal
     *             error.
     * @om
     */
    public static ItemFoXmlProvider getInstance() {
        return PROVIDER;
    }

    /**
     * 
     * @param values
     * @return
     * @throws WebserverSystemException
     */
    public final String getItemFoXml(final Map values)
        throws WebserverSystemException {

        return getXml(ITEM_RESOURCE_NAME, ITEM_PATH, values);
    }

    /**
     * 
     * @param values
     * @return
     * @throws WebserverSystemException
     */
    public final String getComponentFoXml(final Map values)
        throws WebserverSystemException {

        return getXml(COMPONENT_RESOURCE_NAME, COMPONENT_PATH, values);
    }

    /**
     * Get FOXML for Content.
     * 
     * @param values
     *            Map with values for template.
     * @return FOXML part for Content.
     * @throws WebserverSystemException
     */
    public String getContentFoXml(final Map values)
        throws WebserverSystemException {

        return getXml(CONTENT_RESOURCE_NAME, COMPONENT_PATH, values);
    }

    /**
     * 
     * @param values
     * @return
     * @throws WebserverSystemException
     */
    public final String getComponentDefaultDc(final Map values)
        throws WebserverSystemException {

        return getXml(DEFAULT_DC_RESOURCE_NAME, COMPONENT_PATH, values);
    }

    /**
     * 
     * @param values
     * @return
     * @throws WebserverSystemException
     */
    public final String getItemRelsExt(final Map values)
        throws WebserverSystemException {

        return getXml(RELS_EXT_RESOURCE_NAME, ITEM_RELS_EXT_PATH, values);
    }

    /**
     * 
     * @param values
     * @return
     * @throws WebserverSystemException
     */
    public final String getComponentRelsExt(final Map values)
        throws WebserverSystemException {

        return getXml(RELS_EXT_RESOURCE_NAME, COMPONENT_RELS_EXT_PATH, values);
    }

    /**
     * Render (an initial) WOV.
     * 
     * @param values
     * @return
     * @throws WebserverSystemException
     */
    public String getWov(final Map<String, ?> values)
        throws WebserverSystemException {

        return getXml(WOV_RESOURCE_NAME, COMMON_TEMPLATE_PATH, values);
    }
}
