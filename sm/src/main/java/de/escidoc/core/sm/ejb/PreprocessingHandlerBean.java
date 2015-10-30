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

import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.sm.ejb.interfaces.PreprocessingHandlerLocal;
import de.escidoc.core.sm.ejb.interfaces.PreprocessingHandlerRemote;
import de.escidoc.core.sm.service.interfaces.PreprocessingHandlerInterface;

@Stateless(name = "PreprocessingHandler")
@Remote(PreprocessingHandlerRemote.class)
@Local(PreprocessingHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class PreprocessingHandlerBean implements PreprocessingHandlerRemote, PreprocessingHandlerLocal {

    private PreprocessingHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(PreprocessingHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (PreprocessingHandlerInterface) BeanLocator.getBean("PreprocessingHandler.spring.ejb.context",
                    "service.PreprocessingHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception PreprocessingHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public void preprocess(
        final String aggregationDefinitionId, final String xmlData, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, XmlSchemaValidationException, XmlCorruptedException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.preprocess(aggregationDefinitionId, xmlData);
    }

    @PermitAll
    public void preprocess(
        final String aggregationDefinitionId, final String xmlData, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, XmlSchemaValidationException, XmlCorruptedException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.preprocess(aggregationDefinitionId, xmlData);
    }
}