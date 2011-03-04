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
package de.escidoc.core.om.business.stax.handler.context;

import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.business.fedora.Utility;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.notfound.OrganizationalUnitNotFoundException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyAttributeViolationException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyElementViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.Elements;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author SWA
 * 
 */
public class ContextPropertiesUpdateHandler extends DefaultHandler {

    private static final AppLogger log =
        new AppLogger(ContextPropertiesUpdateHandler.class.getName());

    private StaxParser parser = null;

    private String propertiesPath = "/context/properties";

    private String contextId = null;

    private Map<String, String> changedValuesInRelsExt = null;

    private Map<String, String> changedValuesInDc = null;

    private List<String> deletableValues = null;

    private Map<String, String> valuesToAdd = null;

    private final List<String> orgunits = new ArrayList<String>();

    private static final String organizationalUnitPath =
        "/context/properties/organizational-units/organizational-unit";

    /**
     * Handler to update Context properties.
     * 
     * @param contextId
     *            Id of the Context object.
     * @param parser
     *            StaxParser
     */
    public ContextPropertiesUpdateHandler(final String contextId,
        final StaxParser parser) {
        this.contextId = contextId;
        this.propertiesPath = "/context/properties";
        this.parser = parser;

        this.changedValuesInDc = new HashMap<String, String>();
        this.changedValuesInRelsExt = new HashMap<String, String>();
        deletableValues = new ArrayList<String>();
        valuesToAdd = new HashMap<String, String>();
        deletableValues.add(Elements.ELEMENT_DESCRIPTION);
    }

    /**
     * Handle the start of an element.
     * 
     * @param element
     *            The element.
     * @return The element.
     * @throws MissingAttributeValueException
     * @throws OrganizationalUnitNotFoundException
     * @throws InvalidStatusException
     * @throws SystemException
     * @om
     */
    @Override
    public StartElement startElement(final StartElement element)
        throws InvalidContentException, ReadonlyElementViolationException,
        ReadonlyAttributeViolationException, MissingAttributeValueException,
        OrganizationalUnitNotFoundException, InvalidStatusException,
        SystemException {

        final String currentPath = parser.getCurPath();
        // String theName = element.getLocalName();

        if (organizationalUnitPath.equals(currentPath)) {
            final String id = XmlUtility.getIdFromStartElement(element);

            Utility.getInstance().checkIsOrganizationalUnit(id);

            final String orgUnitStatus =
                TripleStoreUtility.getInstance().getPropertiesElements(id,
                    TripleStoreUtility.PROP_PUBLIC_STATUS);

            if (!orgUnitStatus
                .equals(de.escidoc.core.common.business.Constants.STATUS_OU_OPENED)) {
                throw new InvalidStatusException("Organizational unit with id "
                        + id
                        + " should be in status "
                        + de.escidoc.core.common.business.Constants.STATUS_OU_OPENED
                        + " but is in status " + orgUnitStatus);
            }
            this.orgunits.add(id);
        }



        return element;
    }

    @Override
    public EndElement endElement(final EndElement element) throws Exception {
        return element;
    }

    @Override
    public String characters(final String data, final StartElement element)
        throws Exception {
        final String curPath = parser.getCurPath();

        if (curPath.startsWith(propertiesPath)) {
            // name
            if (curPath.equals(propertiesPath + '/' + Elements.ELEMENT_NAME)) {
                if (data.length() == 0) {
                    throw new MissingElementValueException(
                        "element 'name' is empty.");
                }
                if (checkValueChanged(Elements.ELEMENT_NAME, data)) {
                    this.changedValuesInDc.put(Elements.ELEMENT_NAME, data);
                }
            }

            // type
            else if (curPath.equals(propertiesPath + '/'
                + Elements.ELEMENT_TYPE)) {
                if (data.length() == 0) {
                    throw new MissingElementValueException(
                        "element 'type' is empty.");
                }
                if (checkValueChanged(Elements.ELEMENT_TYPE, data)) {
                    this.changedValuesInRelsExt
                        .put(Elements.ELEMENT_TYPE, data);
                }

            }
            // description
            else if (curPath.equals(propertiesPath + '/'
                + Elements.ELEMENT_DESCRIPTION)) {
                deletableValues.remove(Elements.ELEMENT_DESCRIPTION);
                if (TripleStoreUtility.getInstance().getPropertiesElements(
                    contextId,
                    de.escidoc.core.common.business.Constants.DC_NS_URI
                        + Elements.ELEMENT_DESCRIPTION) == null) {
                    valuesToAdd.put(Elements.ELEMENT_DESCRIPTION, data);
                }
                else {
                    if (checkValueChanged(Elements.ELEMENT_DESCRIPTION, data)) {
                        this.changedValuesInDc.put(
                            Elements.ELEMENT_DESCRIPTION, data);
                    }
                }

            }

        }

        return data;
    }

    /**
     * Return HashMap of values which are not equal to repository (TripleStore).
     * 
     * @return changed values
     */
    public Map<String, String> getChangedValuesInRelsExt() {
        return (this.changedValuesInRelsExt);
    }

    /**
     * Return HashMap of values which are not equal to repository (TripleStore).
     * 
     * @return changed values
     */
    public Map<String, String> getChangedValuesInDc() {
        return (this.changedValuesInDc);
    }



    // FIXME ? This check requires triplestore access. Just set new datastream
    // and leave it to the resource to check if it is changed!? (FRS)
    /**
     * Check if value equals repository entry (only TripleStrore entries).
     * 
     * 
     * @param key
     *            search key
     * @param value
     *            value
     * @return true If value does not compares to the reopsitory value. false If
     *         value compares to the respository value.
     * @throws SystemException
     *             In case of TripeStore access error.
     */
    private boolean checkValueChanged(final String key, final String value)
        throws SystemException {
        final String repositoryValue;

        repositoryValue = key.equals(Elements.ELEMENT_DESCRIPTION) ? TripleStoreUtility.getInstance().getPropertiesElements(
                contextId,
                de.escidoc.core.common.business.Constants.DC_NS_URI + key) : key.equals(Elements.ELEMENT_NAME) ? TripleStoreUtility.getInstance().getTitle(contextId) : TripleStoreUtility.getInstance().getPropertiesElements(
                contextId,
                de.escidoc.core.common.business.Constants.PROPERTIES_NS_URI
                        + key);

        boolean changed = false;
        if (!XmlUtility.escapeForbiddenXmlCharacters(value).equals(
            repositoryValue)) {
            changed = true;
        }

        return (changed);
    }

    /**
     * 
     * @return
     */
    public List<String> getPropertiesToRemove() {
        return deletableValues;
    }

    /**
     * 
     * @return
     */
    public Map<String, String> getPropertiesToAdd() {
        return valuesToAdd;
    }

    /**
     * Get the organizational units of the Context.
     * 
     * @return organizational units
     */
    public List<String> getOrganizationalUnits() {
        return this.orgunits;
    }

}
