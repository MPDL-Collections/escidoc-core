package de.escidoc.core.sm.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.CreateException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.sm.ejb.interfaces.StatisticDataHandlerLocal;
import de.escidoc.core.sm.ejb.interfaces.StatisticDataHandlerRemote;
import de.escidoc.core.sm.service.interfaces.StatisticDataHandlerInterface;

@Stateless(name = "StatisticDataHandler")
@Remote(StatisticDataHandlerRemote.class)
@Local(StatisticDataHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class StatisticDataHandlerBean implements StatisticDataHandlerRemote, StatisticDataHandlerLocal {

    private StatisticDataHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticDataHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (StatisticDataHandlerInterface) BeanLocator.getBean("StatisticDataHandler.spring.ejb.context",
                    "service.StatisticDataHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception StatisticDataHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public void create(final String xmlData, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.create(xmlData);
    }

    @PermitAll
    public void create(final String xmlData, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.create(xmlData);
    }
}