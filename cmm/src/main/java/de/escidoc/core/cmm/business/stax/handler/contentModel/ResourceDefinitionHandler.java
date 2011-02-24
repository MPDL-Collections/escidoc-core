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
package de.escidoc.core.cmm.business.stax.handler.contentModel;

import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.fedora.resources.create.ResourceDefinitionCreate;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;

import javax.naming.directory.NoSuchAttributeException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler to extract resource definitions from content model xml by StaxParser.
 * 
 * @author FRS
 * 
 * @om
 */
public class ResourceDefinitionHandler extends DefaultHandler {

    private static final AppLogger LOG = new AppLogger(
        ResourceDefinitionHandler.class.getName());

    private final StaxParser parser;

    private String resourceDefinitionPath =
        "/content-model/resource-definitions/resource-definition";

    private Map<String, ResourceDefinitionCreate> resourceDefinitions = null;

    private ResourceDefinitionCreate resourceDefinition;

    /**
     * Returns the resource definitions extracted by this content handler.
     * 
     * @return The resource definitions.
     */
    public final Map<String, ResourceDefinitionCreate> getResourceDefinitions() {
        return resourceDefinitions;
    }

    /**
     * Instantiate a MdRecordDefinitionHandler.
     * 
     * @param parser
     *            The parser.
     * @param path
     *            Element path to resource definitions (e.g.
     *            /content-model/resource-definitions).
     */
    public ResourceDefinitionHandler(final StaxParser parser, final String path) {

        this.parser = parser;
        this.resourceDefinitionPath = path + "/resource-definition";
        this.resourceDefinitions =
            new HashMap<String, ResourceDefinitionCreate>();
    }

    /**
     * Handle the start of an element.
     * 
     * @param element
     *            The element.
     * @return The element.
     * @throws MissingAttributeValueException
     *             If a required element is not set.
     * @throws InvalidContentException
     *             If invalid content is found.
     * @throws WebserverSystemException
     *             If an error occurs.
     * @see de.escidoc.core.common.util.xml.stax.handler.DefaultHandler#startElement
     *      (de.escidoc.core.common.util.xml.stax.events.StartElement)
     */
    @Override
    public final StartElement startElement(final StartElement element)
        throws MissingAttributeValueException, InvalidContentException,
        WebserverSystemException {

        final String currentPath = parser.getCurPath();
        if (currentPath.equals(this.resourceDefinitionPath)) {

            LOG.debug("Parser reached " + this.resourceDefinitionPath);

            this.resourceDefinition = new ResourceDefinitionCreate();
            try {
                this.resourceDefinition.setName(element.getAttributeValue(null,
                    "name"));
            }
            catch (NoSuchAttributeException e) {
                throw new InvalidContentException(
                    "Name required in resource definition.", e);
            }
        }
        else if (currentPath.equals(this.resourceDefinitionPath + "/xslt")) {
            try {
                this.resourceDefinition.setXsltHref(element.getAttributeValue(
                    Constants.XLINK_NS_URI, "href"));
            }
            catch (NoSuchAttributeException e) {
                // TODO allow inline XSLT?
                throw new InvalidContentException(
                    "The element 'xslt' must have the attribute 'href'.", e);
            }
            catch (MalformedURLException e) {
                throw new InvalidContentException(e);
            }
            catch (IOException e) {
                throw new WebserverSystemException(
                    "Configuration could not be read.", e);
            }
        }

        return element;
    }

    /**
     * 
     * 
     * @param data
     * @param element
     * 
     * @return The character data for further processing.
     */
    @Override
    public final String characters(final String data, final StartElement element) {

        final String currentPath = parser.getCurPath();
        if (currentPath.equals(this.resourceDefinitionPath + "/md-record-name")) {
            this.resourceDefinition.setMdRecordName(data);
        }
        return data;
    }

    /**
     * 
     * 
     * @param element
     * 
     * @return The element for further processing.
     */
    @Override
    public final EndElement endElement(final EndElement element) {

        final String currentPath = parser.getCurPath();
        if (currentPath.equals(this.resourceDefinitionPath)) {
            if (this.resourceDefinitions == null) {
                this.resourceDefinitions =
                    new HashMap<String, ResourceDefinitionCreate>();
            }
            this.resourceDefinitions.put(this.resourceDefinition.getName(),
                this.resourceDefinition);
        }

        return element;
    }

}
