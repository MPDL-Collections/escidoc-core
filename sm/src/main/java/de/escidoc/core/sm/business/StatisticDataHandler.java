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
package de.escidoc.core.sm.business;

import de.escidoc.core.common.business.queue.StatisticQueueHandler;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ScopeNotFoundException;
import de.escidoc.core.common.exceptions.system.SqlDatabaseSystemException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.sm.business.interfaces.StatisticDataHandlerInterface;
import de.escidoc.core.sm.business.persistence.SmStatisticDataDaoInterface;

/**
 * An statistic data resource handler.
 * 
 * @spring.bean id="business.StatisticDataHandler" scope="prototype"
 * @author MIH
 * @sm
 */
public class StatisticDataHandler implements StatisticDataHandlerInterface {

    private static AppLogger log =
        new AppLogger(StatisticDataHandler.class.getName());

    private StatisticQueueHandler statisticQueueHandler = null;

    private SmStatisticDataDaoInterface dao;

    private SmXmlUtility xmlUtility;

    /**
     * See Interface for functional description.
     * 
     * @see de.escidoc.core.sm.business.interfaces .StatisticDataHandlerInterface
     *      #create(java.lang.String)
     * 
     * @param xmlData
     *            statistic data as xml in statistic-data schema.
     * 
     * @throws MissingMethodParameterException
     *             ex
     * @throws SystemException
     *             ex
     * 
     * @sm
     */
    public void create(final String xmlData)
        throws MissingMethodParameterException, SystemException {
        if (log.isDebugEnabled()) {
            log.debug("StatisticDataHandler does create");
        }
        if (xmlData == null || xmlData.equals("")) {
            log.error("xml may not be null");
            throw new MissingMethodParameterException("xml may not be null");
        }
        statisticQueueHandler.putMessage(xmlData);
    }

    /**
     * See Interface for functional description.
     * 
     * @see de.escidoc.core.sm.business.interfaces .StatisticDataHandlerInterface
     *      #insertStatisticData(java.lang.String)
     * 
     * @param xmlData
     *            xmlData
     * 
     * @throws ScopeNotFoundException
     *             ex
     * @throws MissingMethodParameterException
     *             ex
     * @throws XmlSchemaValidationException
     *             ex
     * @throws XmlCorruptedException
     *             ex
     * @throws SystemException
     *             e
     */
    public void insertStatisticData(final String xmlData)
        throws ScopeNotFoundException, MissingMethodParameterException,
        XmlSchemaValidationException, XmlCorruptedException, SystemException {
        if (xmlData == null || xmlData.equals("")) {
            log.error("xml may not be null");
            throw new MissingMethodParameterException("xml may not be null");
        }
        XmlUtility.validate(xmlData, XmlUtility
            .getStatisticDataSchemaLocation());

        String scopeId = xmlUtility.getScopeId(xmlData);

        if (scopeId == null || scopeId.equals("")) {
            log.error("scopeId is null");
            throw new ScopeNotFoundException("scopeId is null");
        }
        try {
            dao.saveStatisticData(xmlData, scopeId);
        }
        catch (SqlDatabaseSystemException e) {
            if (e.getCause() != null
                && e.getCause().getClass() != null
                && e.getCause().getClass().getSimpleName().equals(
                    "ConstraintViolationException")) {
                log
                    .error("scope with id " + scopeId
                        + " not found in database");
                throw new ScopeNotFoundException("scope with id " + scopeId
                    + " not found in database");
            }
            else {
                log.error(e);
                throw (e);
            }
        }
    }

    /**
     * Setter for the StatisticQueueHandler.
     * 
     * @spring.property ref="common.StatisticQueueHandler"
     * @param statisticQueueHandler
     *            StatisticQueueHandler
     */
    public void setStatisticQueueHandler(
        final StatisticQueueHandler statisticQueueHandler) {
        this.statisticQueueHandler = statisticQueueHandler;
    }

    /**
     * Setter for the dao.
     * 
     * @spring.property ref="persistence.SmStatisticDataDao"
     * @param dao
     *            The data access object.
     * 
     * @sm
     */
    public void setDao(final SmStatisticDataDaoInterface dao) {
        this.dao = dao;
    }

    /**
     * Setting the xmlUtility.
     * 
     * @param xmlUtility
     *            The xmlUtility to set.
     * @spring.property ref="business.sm.XmlUtility"
     */
    public final void setXmlUtility(final SmXmlUtility xmlUtility) {
        this.xmlUtility = xmlUtility;
    }

}
