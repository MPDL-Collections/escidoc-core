package de.escidoc.core.om.ejb;

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

import de.escidoc.core.common.exceptions.application.invalid.InvalidTripleStoreOutputFormatException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidTripleStoreQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.ejb.interfaces.SemanticStoreHandlerLocal;
import de.escidoc.core.om.ejb.interfaces.SemanticStoreHandlerRemote;
import de.escidoc.core.om.service.interfaces.SemanticStoreHandlerInterface;

@Stateless(name = "SemanticStoreHandler")
@Remote(SemanticStoreHandlerRemote.class)
@Local(SemanticStoreHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class SemanticStoreHandlerBean implements SemanticStoreHandlerRemote, SemanticStoreHandlerLocal {

    private SemanticStoreHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(SemanticStoreHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("SemanticStoreHandler.spring.ejb.context").getFactory();
            this.service = (SemanticStoreHandlerInterface) factory.getBean("service.SemanticStoreHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception SemanticStoreHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String spo(final String taskParam, final SecurityContext securityContext) throws SystemException,
        InvalidTripleStoreQueryException, InvalidTripleStoreOutputFormatException, InvalidXmlException,
        MissingElementValueException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.spo(taskParam);
    }

    @PermitAll
    public String spo(final String taskParam, final String authHandle, final Boolean restAccess)
        throws SystemException, InvalidTripleStoreQueryException, InvalidTripleStoreOutputFormatException,
        InvalidXmlException, MissingElementValueException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.spo(taskParam);
    }
}