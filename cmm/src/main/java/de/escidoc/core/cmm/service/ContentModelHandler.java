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
package de.escidoc.core.cmm.service;

import java.net.MalformedURLException;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import de.escidoc.core.cmm.business.interfaces.ContentModelHandlerInterface;
import de.escidoc.core.common.business.fedora.EscidocBinaryContent;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentStreamNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyVersionException;
import de.escidoc.core.common.exceptions.application.violated.ResourceInUseException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Implementation for the Service layer of the ctm component.
 * 
 * @spring.bean id="service.ContentModelHandler" scope="prototype"
 * @interface 
 *            class="de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface"
 * @author MSC
 * @ctm
 * @service
 */

public class ContentModelHandler
    implements
    de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface {

    private ContentModelHandlerInterface business;

    /**
     * Setter for the business object.
     * 
     * @spring.property ref="business.FedoraContentModelHandler"
     * @param business
     *            business object.
     * @service.exclude
     * @ctm
     */
    public void setBusiness(final ContentModelHandlerInterface business) {
        this.business = business;
    }

    // CHECKSTYLE:JAVADOC-OFF

    /**
     * See Interface for functional description.
     * 
     * @param xmlData
     * @return
     * @throws SystemException
     * @throws MissingAttributeValueException
     * @throws InvalidContentException
     * @throws InvalidXmlException
     * @throws InvalidXmlException
     * @throws SystemException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MissingMethodParameterException
     * @throws XMLStreamException
     * @throws MalformedURLException
     * @see de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface#create(java.lang.String)
     */
    public String create(final String xmlData) throws InvalidContentException,
        MissingAttributeValueException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException, XmlCorruptedException,
        XmlSchemaValidationException {
        return business.create(xmlData);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @throws SystemException
     * @throws ContentModelNotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MissingMethodParameterException
     * @throws InvalidStatusException
     * @throws LockingException
     * @throws ResourceInUseException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #delete(java.lang.String)
     */
    public void delete(final String id) throws SystemException,
        ContentModelNotFoundException, AuthenticationException,
        AuthorizationException, MissingMethodParameterException,
        LockingException, InvalidStatusException, ResourceInUseException {
        business.delete(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @throws ContentModelNotFoundException
     * @throws SystemException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #retrieve(java.lang.String)
     */
    public String retrieve(final String id)
        throws ContentModelNotFoundException, SystemException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException {
        return business.retrieve(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @throws ContentModelNotFoundException
     * @throws SystemException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #retrieve(java.lang.String)
     */
    public String retrieveProperties(final String id)
        throws ContentModelNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        return business.retrieveProperties(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @throws ContentModelNotFoundException
     * @throws SystemException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #retrieveContentStreams(java.lang.String)
     */
    public String retrieveContentStreams(final String id)
        throws ContentModelNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        return business.retrieveContentStreams(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @throws ContentModelNotFoundException
     * @throws SystemException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #retrieveContentStream(java.lang.String)
     */
    public String retrieveContentStream(final String id, final String name)
        throws ContentModelNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        return business.retrieveContentStream(id, name);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param name
     * @return
     * @throws ContentModelNotFoundException
     * @throws SystemException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MissingMethodParameterException
     * @throws ContentStreamNotFoundException
     * @throws InvalidStatusException
     * @see de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #retrieveContentStreamContent(java.lang.String, java.lang.String)
     * @axis.exclude
     */
    public EscidocBinaryContent retrieveContentStreamContent(
        final String id, final String name)
        throws ContentModelNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ContentStreamNotFoundException,
        InvalidStatusException {
        return business.retrieveContentStreamContent(id, name);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @throws ContentModelNotFoundException
     * @throws SystemException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #retrieveResources(java.lang.String)
     * @axis.exclude
     */
    public String retrieveResources(final String id)
        throws ContentModelNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        return business.retrieveResources(id);
    }

    public String retrieveVersionHistory(final String id)
        throws ContentModelNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        return business.retrieveVersionHistory(id);
    }

    /**
     * Retrieves a filtered list of Content Models.
     * 
     * @param parameterMap
     *            map of key - value pairs describing the filter
     * 
     * @return Returns XML representation of the list of Content Model objects.
     * @throws InvalidSearchQueryException
     *             Thrown if the given search query could not be translated into
     *             a SQL query.
     * @throws SystemException
     *             Thrown in case of an internal error.
     */
    public String retrieveContentModels(final Map<String, String[]> parameterMap)
        throws InvalidSearchQueryException, SystemException {
        return business.retrieveContentModels(parameterMap);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param xmlData
     * @return
     * @throws InvalidXmlException
     * @throws ContentModelNotFoundException
     * @throws OptimisticLockingException
     * @throws SystemException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MissingMethodParameterException
     * @throws ReadonlyVersionException
     * @throws XMLStreamException
     * @throws InvalidContentException
     * @throws MissingAttributeValueException
     * @see package
     *      de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface
     *      #update(java.lang.String, java.lang.String)
     */
    public String update(final String id, final String xmlData)
        throws InvalidXmlException, ContentModelNotFoundException,
        OptimisticLockingException, SystemException, AuthenticationException,
        AuthorizationException, MissingMethodParameterException,
        ReadonlyVersionException, MissingAttributeValueException,
        InvalidContentException {
        return business.update(id, xmlData);
    }

    /**
     * @see de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface#retrieveMdRecordDefinitionSchemaContent(java.lang.String,
     *      java.lang.String)
     * 
     * @axis.exclude
     * @cmm
     */
    public EscidocBinaryContent retrieveMdRecordDefinitionSchemaContent(
        final String id, final String name) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException,
        ContentModelNotFoundException, SystemException {
        return business.retrieveMdRecordDefinitionSchemaContent(id, name);
    }

    /**
     * 
     * @axis.exclude
     * @cmm
     */
    public EscidocBinaryContent retrieveResourceDefinitionXsltContent(
        final String id, final String name) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException,
        ContentModelNotFoundException, ResourceNotFoundException,
        SystemException {
        return business.retrieveResourceDefinitionXsltContent(id, name);
    }

    // CHECKSTYLE:JAVADOC-ON
}
