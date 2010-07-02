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
package de.escidoc.core.om.business.stax.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.naming.directory.NoSuchAttributeException;

import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.notfound.ReferencedResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RelationPredicateNotFoundException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyAttributeViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.common.util.xml.stax.events.Attribute;
import de.escidoc.core.common.util.xml.stax.events.EndElement;
import de.escidoc.core.common.util.xml.stax.events.StartElement;
import de.escidoc.core.common.util.xml.stax.handler.DefaultHandler;
import de.escidoc.core.om.business.fedora.OntologyUtility;

/**
 * 
 * 
 * @author ROF
 * 
 */
public class ContentRelationsCreateHandler extends DefaultHandler {

    private StaxParser parser;

    private String id;

    public static final String CONTAINER = "/container";

    boolean inContentRelation = false;

    int number = 0;

    private String currentPath = null;

    private String contentRelationsPath = null;

    private String contentRelationPath = null;

    private String targetId = null;

    private String targetIdWithoutVersion = null;

    private String targetVersion = null;

    private String predicate = null;

    private Vector<Map<String, String>> relationsData = null;

    private static AppLogger log =
        new AppLogger(ContentRelationsCreateHandler.class.getName());

    /**
     * Instantiate a ContentRelationsCreateHandler.
     * 
     * @param id
     *            The id of the parsed object.
     * @param parser
     *            The parser.
     */
    public ContentRelationsCreateHandler(final String id,
        final StaxParser parser) {
        this.id = id;
        this.parser = parser;
    }

    /**
     * Handle the start of an element.
     * 
     * @param element
     *            The element.
     * @return The element.
     * @throws SystemException
     *             Thrown in case of an internal error.
     * @see de.escidoc.core.common.util.xml.stax.handler.DefaultHandler#startElement
     *      (de.escidoc.core.common.util.xml.stax.events.StartElement)
     */
    public StartElement startElement(final StartElement element)
        throws ReadonlyAttributeViolationException, InvalidContentException,
        ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, SystemException {

        currentPath = parser.getCurPath();
        contentRelationsPath = "/item/relations";
        contentRelationPath = "/item/relations/relation";

        if (currentPath.startsWith(CONTAINER)) {
            contentRelationsPath = "/container/relations";
            contentRelationPath = "/container/relations/relation";
        }
        
        String theName = element.getLocalName();

        if (contentRelationPath.equals(currentPath)) {
            inContentRelation = true;
            number++;
            int indexOfObjId = element.indexOfAttribute(null, "objid");
            if (indexOfObjId != (-1)) {

                String message =
                    "Read only attribute \"objid\" of the " + "element "
                        + element.getLocalName()
                        + " may not exist while create";
                log.info(message);
                throw new ReadonlyAttributeViolationException(message);
            }

        }
        if (inContentRelation) {
            if (theName.equals("target")) {
                checkRefElement(element);
            }
            else if (theName.equals("predicate")) {
                try {
                    String xlinkHref =
                        element
                            .getAttribute(Constants.XLINK_URI, "href")
                            .getValue();
                    if (!OntologyUtility.checkPredicate(xlinkHref)) {
                        String message =
                            "Predicate " + xlinkHref + " is wrong. ";
                        throw new RelationPredicateNotFoundException(message);
                    }
                    else {
                        predicate = xlinkHref;
                    }

                }
                catch (NoSuchAttributeException e) {
                    String message =
                        "Attribute 'href' of the element '" + theName
                            + "' is missing.";
                    log.info(message);
                    throw new InvalidContentException(message);
                }
                int indexOfType =
                    element.indexOfAttribute(Constants.XLINK_URI, "type");

                Attribute type = element.getAttribute(indexOfType);
                String typeValue = type.getValue();
                if (!typeValue.equals(Constants.XLINK_TYPE_SIMPLE)) {
                    String message =
                        "Attribute " + Constants.XLINK_URI + ":"
                            + "type must be set to 'simple'";
                    throw new InvalidContentException(message);
                }

            }
        }
        else if (contentRelationsPath.equals(currentPath)) {
            relationsData = new Vector<Map<String, String>>();
            int indexOfTitle =
                element.indexOfAttribute(Constants.XLINK_URI, "title");

            if (indexOfTitle != (-1)) {
                String message =
                    "Read only attribute \"title\" of the " + "element "
                        + element.getLocalName()
                        + " may not exist while create";
                log.info(message);
                throw new ReadonlyAttributeViolationException(message);
            }
            int indexOfHref =
                element.indexOfAttribute(Constants.XLINK_URI, "href");

            if (indexOfHref != (-1)) {
                String message =
                    "Read only attribute \"href\" of the " + "element "
                        + element.getLocalName()
                        + " may not exist while create";
                log.error(message);
                throw new ReadonlyAttributeViolationException(message);
            }
            try {
                Attribute type =
                    element.getAttribute(Constants.XLINK_URI, "type");
                if (!type.getValue().equals("simple")) {
                    String message =
                        "Attribute " + Constants.XLINK_URI + ":"
                            + "type must be set to 'simple'";
                    throw new InvalidContentException(message);
                }
            }
            catch (NoSuchAttributeException e) {
                element.addAttribute(new Attribute("type", Constants.XLINK_URI,
                    Constants.XLINK_PREFIX, "simple"));
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
     */
    public EndElement endElement(final EndElement element) {
        if (inContentRelation && element.getLocalName().equals("relation")) {
            inContentRelation = false;
            Map<String, String> relationData = new HashMap<String, String>();
            relationsData.add(relationData);
            relationData.put("predicate", predicate);
            relationData.put("target", targetIdWithoutVersion);
            relationData.put("targetVersion", targetVersion);
            targetId = null;
            targetIdWithoutVersion = null;
            targetVersion = null;
            predicate = null;
        }
        return element;
    }

    /**
     * @return Returns the title.
     */
    public Vector<Map<String, String>> getContentRelationsData() {
        return relationsData;
    }

    /**
     * 
     * @param element
     * @throws InvalidContentException
     * @throws ReadonlyAttributeViolationException
     * @throws ReferencedResourceNotFoundException
     * @throws SystemException
     */
    private void checkRefElement(StartElement element)
        throws InvalidContentException, ReadonlyAttributeViolationException,
        ReferencedResourceNotFoundException, SystemException {
        try {
            String objectId = element.getAttribute(null, "objid").getValue();
            String xlinkHref =
                element.getAttribute(Constants.XLINK_URI, "href").getValue();
            targetId = XmlUtility.getIdFromURI(xlinkHref);
            if (!objectId.equals(targetId)) {
                String message =
                    "Value of the attribute 'href' is wrong. It must contain "
                        + objectId + " instead of " + targetId;
                log.info(message);
                throw new InvalidContentException(message);
            }
            targetIdWithoutVersion =
                XmlUtility.getObjidWithoutVersion(targetId);
            targetVersion = targetId.replaceFirst(targetIdWithoutVersion, "");
            if (targetVersion.length() > 0) {
                targetVersion = targetVersion.substring(1);
            }
            else {
                targetVersion = null;
            }

            String targetObjectType =
                TripleStoreUtility.getInstance().getObjectType(
                    targetIdWithoutVersion);
            targetExist(targetObjectType);
            if (!xlinkHref.equals("/ir/" + targetObjectType + "/" + targetId)) {
                String message =
                    "Value of the attribute 'href' is wrong. It must be"
                        + "/ir/" + targetObjectType + "/" + targetId;
                log.info(message);
                throw new InvalidContentException(message);
            }

            int indexOfTitle =
                element.indexOfAttribute(Constants.XLINK_URI, "title");
            if (indexOfTitle != (-1)) {
                String message =
                    "Read only attribute \"title\" of the " + "element "
                        + element.getLocalName()
                        + " may not exist while create";
                log.info(message);
                throw new ReadonlyAttributeViolationException(message);
            }
            // targetId = "<info:fedora/" + targetId + ">";

        }
        catch (NoSuchAttributeException e) {
            String msg =
                "Expected attribute in object reference " + "in 'relation' of "
                    + id + " is not set. (create item)";
            log.info(msg, e);
            throw new InvalidContentException(msg, e);

        }
    }

    /**
     * 
     * @param targetObjectType
     * @throws ReferencedResourceNotFoundException
     * @throws SystemException
     */
    private void targetExist(final String targetObjectType)
        throws ReferencedResourceNotFoundException, SystemException {
        if (!TripleStoreUtility.getInstance().exists(targetIdWithoutVersion)) {
            String message =
                "Referenced target resource with id " + targetIdWithoutVersion
                    + " does not exist.";
            log.error(message);
            throw new ReferencedResourceNotFoundException(message);

        }
        if (targetVersion != null) {
            String targetLatestVersion = null;
            if (targetObjectType == "item") {
                targetLatestVersion =
                    TripleStoreUtility.getInstance().getPropertiesElements(
                        targetIdWithoutVersion,
                        TripleStoreUtility.PROP_LATEST_VERSION_NUMBER);
            }

            else if (targetObjectType == "container") {
                targetLatestVersion =
                    TripleStoreUtility.getInstance().getPropertiesElements(
                        targetIdWithoutVersion,
                        TripleStoreUtility.PROP_LATEST_VERSION_NUMBER);
            }
            if (Integer.parseInt(targetVersion) > Integer
                .parseInt(targetLatestVersion)) {
                String message =
                    "Referenced target resource with id "
                        + targetIdWithoutVersion + ":" + targetVersion
                        + " does not exist.";
                log.info(message);
                throw new ReferencedResourceNotFoundException(message);
            }
        }
    }

}
