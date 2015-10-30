package de.escidoc.core.tme.ejb;

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

import de.escidoc.core.common.exceptions.application.invalid.TmeException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.tme.ejb.interfaces.JhoveHandlerLocal;
import de.escidoc.core.tme.ejb.interfaces.JhoveHandlerRemote;
import de.escidoc.core.tme.service.interfaces.JhoveHandlerInterface;

@Stateless(name = "JhoveHandler")
@Remote(JhoveHandlerRemote.class)
@Local(JhoveHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@RunAs("Administrator")
public class JhoveHandlerBean implements JhoveHandlerRemote, JhoveHandlerLocal {

    private JhoveHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(JhoveHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (JhoveHandlerInterface) BeanLocator.getBean("JhoveHandler.spring.ejb.context", "service.JhoveHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception JhoveHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String extract(final String requests, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException,
        SystemException, TmeException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.extract(requests);
    }

    @PermitAll
    public String extract(final String requests, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, XmlCorruptedException, XmlSchemaValidationException,
        MissingMethodParameterException, SystemException, TmeException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.extract(requests);
    }
}