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
package org.escidoc.core.oai.internal;

import javax.xml.bind.JAXBElement;

import org.escidoc.core.domain.service.ServiceUtility;
import org.escidoc.core.domain.sru.RequestTypeTO;
import org.escidoc.core.domain.sru.ResponseTypeTO;
import org.escidoc.core.domain.sru.parameters.SruRequestTypeFactory;
import org.escidoc.core.domain.sru.parameters.SruSearchRequestParametersBean;
import org.escidoc.core.oai.OAIsRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.oai.service.interfaces.SetDefinitionHandlerInterface;

/**
 * REST Service Implementation for OAI Set Definition Service.
 *
 * @author SWA
 */
@Service
public class OAIsRestServiceImpl implements OAIsRestService {

    private final static Logger LOG = LoggerFactory.getLogger(OAIsRestServiceImpl.class);

    @Autowired
    @Qualifier("service.SetDefinitionHandler")
    private SetDefinitionHandlerInterface oaiHandler;

    @Autowired
    private ServiceUtility serviceUtility;

    protected OAIsRestServiceImpl() {
    }

    /*
     * (non-Javadoc)
     *
     * @see de.escidoc.core.oai.OAIsRestService#retrieveSetDefinitions(org.escidoc.core.domain.sru.parameters.SruSearchRequestParametersBean)
     */
    @Override
    public JAXBElement<? extends ResponseTypeTO> retrieveSetDefinitions(
            final SruSearchRequestParametersBean parameters)
            throws AuthenticationException,
            AuthorizationException, MissingMethodParameterException, InvalidSearchQueryException, SystemException {

        final JAXBElement<? extends RequestTypeTO> requestTO = SruRequestTypeFactory.createRequestTO(parameters);

        return (JAXBElement<? extends ResponseTypeTO>) serviceUtility.fromXML(
                this.oaiHandler.retrieveSetDefinitions(serviceUtility.toMap(requestTO)));
    }

}