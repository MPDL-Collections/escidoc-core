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

import de.escidoc.core.common.business.fedora.resources.create.MdRecordCreate;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.stax.handler.MultipleExtractor;
import de.escidoc.core.common.util.xml.Elements;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * The MetadataHandler. The parser handles only one Metadata Record!
 * 
 * @author SWA
 * 
 * @om
 */
public class MetadataHandler2 extends DefaultHandler {

    private static final AppLogger LOG =
        new AppLogger(MetadataHandler2.class.getName());

    private final StaxParser parser;

    private String metadataXPath = "//md-record";

    private MdRecordCreate metadataRecord = null;

    private boolean parsingMetadata = false;

    private MultipleExtractor me = null;

    private boolean payloadRootElement = false;

    /**
     * Instantiate a MetaDataHandler.
     * 
     * @param parser
     *            The parser.
     * @param xpath
     *            Metadata Records XPath (e.g. /item/md-records/md-record).
     */
    public MetadataHandler2(final StaxParser parser, final String xpath) {

        this.parser = parser;
        this.metadataXPath = xpath;
    }

    /**
     * Set XPath for metadata record.
     * 
     * @param xpath
     *            Metadata Records XPath (e.g. /item/md-records/md-record).
     */
    public void setMetadataRecordXPath(final String xpath) {

        this.metadataXPath = xpath;
    }

    /**
     * Handle the start of an element.
     * 
     * @param element
     *            The element.
     * @return The element.
     * @throws MissingAttributeValueException
     *             If a required attribute is missing.
     * @throws InvalidContentException
     *             Thrown if md-record name is invalid (not unique)
     *@throws WebserverSystemException
     *             Thrown by MultipleExtractor
     * @see de.escidoc.core.common.util.xml.stax.handler.DefaultHandler#startElement
     *      (de.escidoc.core.common.util.xml.stax.events.StartElement)
     */
    @Override
    public final StartElement startElement(final StartElement element)
        throws MissingAttributeValueException, WebserverSystemException,
        InvalidContentException {

        if (this.parsingMetadata) {
            this.me.startElement(element);

            // extract namespace and attributes 'type' from payload root element
            if (this.payloadRootElement) {
                this.metadataRecord.setNameSpace(element.getNamespace());
                this.payloadRootElement = false;
            }
        }
        else {
            String currentPath = parser.getCurPath();
            if (currentPath.equals(this.metadataXPath)
                && element.indexOfAttribute(null, "inherited") < 0) {

                LOG.debug("Parser reached " + this.metadataXPath);

                this.parsingMetadata = true;

                this.metadataRecord = new MdRecordCreate();

                this.metadataRecord.setName(getAttributeValue(element, null,
                    "name"));
                String schema = getAttributeValue(element, null, "schema");
                if (schema == null) {
                    schema = "unknown";
                }
                this.metadataRecord.setSchema(schema);
                String mdType = getAttributeValue(element, null, "md-type");
                if (mdType == null) {
                    mdType = "unknown";
                }
                this.metadataRecord.setType(mdType);

                this.payloadRootElement = true;
                this.me =
                    new MultipleExtractor(this.metadataXPath, "name", parser);
                this.me.startElement(element);
            }
        }

        return element;
    }

    /**
     * Handle the end of an element.
     * 
     * @param element
     *            The element.
     * @return The element.
     * @see de.escidoc.core.common.util.xml.stax.handler.DefaultHandler#endElement
     *      (de.escidoc.core.common.util.xml.stax.events.EndElement)
     * @om
     */
    @Override
    public final EndElement endElement(final EndElement element)
        throws WebserverSystemException {

        if (this.metadataXPath.equals(parser.getCurPath())
            && this.parsingMetadata) {
            LOG.debug("End of " + this.metadataXPath);

            this.parsingMetadata = false;

            this.me.endElement(element);
            HashMap<String, ?> tmp =
                (HashMap<String, ?>) this.me.getOutputStreams().get(
                    Elements.ELEMENT_MD_RECORDS);
            try {
                this.metadataRecord.setContent((ByteArrayOutputStream) tmp
                    .get(this.metadataRecord.getName()));
            }
            catch (UnsupportedEncodingException e) {
                throw new WebserverSystemException(e);
            }
            this.me = null;
        }
        else if (this.parsingMetadata) {
            this.me.endElement(element);
        }

        return element;
    }

    /**
     * Handle the character section of an element.
     * 
     * @param s
     *            The contents of the character section.
     * @param element
     *            The element.
     * @return The character section.
     * @see de.escidoc.core.common.util.xml.stax.handler.DefaultHandler#characters
     *      (java.lang.String,
     *      de.escidoc.core.common.util.xml.stax.events.StartElement)
     * @om
     */
    @Override
    public final String characters(final String s, final StartElement element)
        throws WebserverSystemException {

        if (this.parsingMetadata) {
            this.me.characters(s, element);
        }

        return s;
    }

    /**
     * Get data structure MetadataRecord.
     * 
     * @return Return the MetadataRecord.
     */
    public final MdRecordCreate getMetadataRecord() {

        return this.metadataRecord;
    }

}
