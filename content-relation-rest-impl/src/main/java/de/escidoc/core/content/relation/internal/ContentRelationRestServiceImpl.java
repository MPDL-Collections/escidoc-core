/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution License, Version 1.0
 * only (the "License"). You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE or http://www.escidoc.de/license. See the License
 * for the specific language governing permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each file and include the License file at
 * license/ESCIDOC.LICENSE. If applicable, add the following below this CDDL HEADER, with the fields enclosed by
 * brackets "[]" replaced with your own identifying information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 * Copyright 2006-2011 Fachinformationszentrum Karlsruhe Gesellschaft fuer wissenschaftlich-technische Information mbH
 * and Max-Planck-Gesellschaft zur Foerderung der Wissenschaft e.V. All rights reserved. Use is subject to license
 * terms.
 */

package de.escidoc.core.content.relation.internal;

import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ContentRelationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ReferencedResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RelationPredicateNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.content.relation.ContentRelationRestService;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import org.escidoc.core.domain.content.relation.ContentRelationTO;
import org.esidoc.core.utils.io.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class ContentRelationRestServiceImpl implements ContentRelationRestService {

    private final static Logger LOG = LoggerFactory.getLogger(ContentRelationRestServiceImpl.class);

    @Autowired
    @Qualifier("service.ContentRelationHandler")
    private ContentRelationHandlerInterface contentRelationHandler;

    private JAXBContext jaxbContext;

    protected ContentRelationRestServiceImpl() {
        try {
            this.jaxbContext = JAXBContext.newInstance(ContentRelationTO.class);
        } catch(JAXBException e) {
            LOG.error("Error on initialising JAXB context.", e);
        }
    }

    @Override
    public ContentRelationTO create(final ContentRelationTO contentRelationTO)
            throws SystemException, InvalidContentException, MissingAttributeValueException,
            RelationPredicateNotFoundException, AuthorizationException, AuthenticationException, InvalidXmlException,
            ReferencedResourceNotFoundException, MissingMethodParameterException {
            return fromXML(this.contentRelationHandler.create(toXML(contentRelationTO)));

    }

    @Override
    public ContentRelationTO retrieve(final String id)
            throws SystemException, AuthorizationException, AuthenticationException, ContentRelationNotFoundException {
        return fromXML(this.contentRelationHandler.retrieve(id));
    }

    @Override
    public ContentRelationTO update(final String id, final ContentRelationTO contentRelationTO)
            throws SystemException, InvalidContentException, OptimisticLockingException, MissingAttributeValueException,
            RelationPredicateNotFoundException, AuthorizationException, InvalidStatusException, AuthenticationException,
            ContentRelationNotFoundException, InvalidXmlException, ReferencedResourceNotFoundException,
            LockingException, MissingMethodParameterException {
        return fromXML(this.contentRelationHandler.update(id, toXML(contentRelationTO)));
    }

    @Override
    public void delete(final String id)
            throws SystemException, AuthorizationException, AuthenticationException, ContentRelationNotFoundException,
            LockingException {
        contentRelationHandler.delete(id);
    }

    // Note: This code is slow and only for migration!
    // TODO: Replace this code and use domain objects!
    private String toXML(ContentRelationTO contentRelationTO) throws SystemException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            final Marshaller marshaller = this.jaxbContext.createMarshaller();
            Stream stream = new Stream();
            marshaller.marshal(contentRelationTO, stream);
            stream.lock();
            stream.writeCacheTo(stringBuilder);
        } catch(Exception e) {
            throw new SystemException("Error on marshalling content relation object.", e);
        }
        return stringBuilder.toString();
    }

    // Note: This code is slow and only for migration!
    // TODO: Replace this code and use domain objects!
    private ContentRelationTO fromXML(String xmlString) throws SystemException {
        ContentRelationTO result = new ContentRelationTO();
        try {
            final Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
            result = (ContentRelationTO) unmarshaller.unmarshal(inputStream);
        } catch(Exception e) {
            throw new SystemException("Error on unmarshalling content relation object.", e);
        }
        return result;
    }
}