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
package de.escidoc.core.om.business.stax.handler.item;

import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.stax.events.Attribute;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;

public class ItemGenericPropertiesHandler extends DefaultHandler {

    private StaxParser parser;

    private String itemId;

    private final String elementPath = "/item/generic-properties";

    public ItemGenericPropertiesHandler(final String itemId,
        final StaxParser parser) {
        this.itemId = itemId;
        this.parser = parser;
    }

    public ItemGenericPropertiesHandler(StaxParser parser) {
        this.parser = parser;
    }

    public StartElement startElement(StartElement element) {

        //String theName = element.getLocalName();
        String currentPath = parser.getCurPath();

        if (this.elementPath.equals(currentPath)) {

            int indexOfHref =
                element.indexOfAttribute(
                    de.escidoc.core.common.business.Constants.XLINK_URI, "href");
            Attribute href = element.getAttribute(indexOfHref);
            href.setValue("/ir/item/" + itemId + "/generic-properties");

        }

        return element;
    }

    public EndElement endElement(EndElement element) {

        return element;
    }

    public String characters(String s, StartElement element) {

        return s;
    }
}
