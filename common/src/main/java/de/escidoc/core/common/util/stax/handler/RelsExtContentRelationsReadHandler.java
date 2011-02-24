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

import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelsExtContentRelationsReadHandler extends DefaultHandler {

    private final StaxParser parser;

    private final List<Map<String, String>> relations =
        new ArrayList<Map<String, String>>();

    private boolean inRdf = false;

    private boolean inRelation = false;

    private static final String PATH = "/RDF/Description";

    private String targetId = null;

    private String predicate = null;

    private static final AppLogger LOGGER = new AppLogger(MultipleExtractor.class.getName());

    public RelsExtContentRelationsReadHandler(final StaxParser parser) {
        this.parser = parser;
    }

    public final List<Map<String, String>> getRelations() {
        return this.relations;
    }

    public final boolean isInRelation() {
        return inRelation;
    }

    public final void setInRelation(final boolean inRelation) {
        this.inRelation = inRelation;
    }

    public final String getTargetId() {
        return targetId;
    }

    public final void setTargetId(final String targetId) {
        this.targetId = targetId;
    }

    public final String getPredicate() {
        return predicate;
    }

    public final void setPredicate(final String predicate) {
        this.predicate = predicate;
    }

    @Override
    public StartElement startElement(final StartElement element)
        throws WebserverSystemException {
        final String curPath = parser.getCurPath();

        if (curPath.equals(PATH)) {
            inRdf = true;
        }
        if ((inRdf)
            && (element.getPrefix().equals(
                Constants.CONTENT_RELATIONS_NS_PREFIX_IN_RELSEXT))) {
            inRelation = true;

            final int indexOfResource =
                element.indexOfAttribute(Constants.RDF_NAMESPACE_URI,
                    "resource");
            if (indexOfResource == -1) {
                final String message =
                        "The attribute 'rdf:resource' of the element '"
                                + element.getLocalName() + "' is missing.";
                LOGGER.error(message);
                throw new WebserverSystemException(message);
            }
            final String resourceValue =
                    element.getAttribute(indexOfResource).getValue();
            final String[] target = resourceValue.split("/");
            targetId = target[1];
            String predicateNs = element.getNamespace();
            predicateNs =
                predicateNs.substring(0, predicateNs.length() - 1);
            final String predicateValue = element.getLocalName();
            predicate = predicateNs + '#' + predicateValue;
        }
        return element;
    }

    @Override
    public EndElement endElement(final EndElement element) {

        if (inRelation) {
            final Map<String, String> relationData =
                new HashMap<String, String>();
            relations.add(relationData);
            relationData.put("predicate", predicate);
            relationData.put("target", targetId);

            targetId = null;
            predicate = null;
            inRelation = false;
        }

        return element;
    }

}
