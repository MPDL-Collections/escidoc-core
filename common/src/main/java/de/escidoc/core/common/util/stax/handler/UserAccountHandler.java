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
package de.escidoc.core.common.util.stax.handler;

import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.stax.events.Attribute;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;

public class UserAccountHandler extends DefaultHandler {

    private StaxParser parser;

    private String accountId = null;

    private String accountName = null;

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    /*
     * 
     */public UserAccountHandler(StaxParser parser) {
        this.parser = parser;

    }

    @Override
    public StartElement startElement(StartElement element)
        throws MissingAttributeValueException {

        String elementPath = "/user-account";

        String theName = element.getLocalName();

        String currenrPath = parser.getCurPath();

        if (currenrPath.equals(elementPath)) {
            int indexOfObjid = element.indexOfAttribute(null, "objid");
            Attribute objid = element.getAttribute(indexOfObjid);
            this.accountId = objid.getValue();
        }
        return element;
    }

    @Override
    public String characters(String data, StartElement element) {
        if (element.getLocalName().equals("name")) {
            this.accountName = data;
        }

        return data;
    }

    @Override
    public EndElement endElement(EndElement element) {

        return element;
    }

}
