package de.escicore.purge.internal;

import de.escicore.purge.PurgeRequest;
import de.escidoc.core.common.business.fedora.FedoraUtility;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Default implementation of {@link de.escicore.purge.PurgeService}.
 *
 * @author <a href="mailto:mail@eduard-hildebrandt.de">Eduard Hildebrandt</a>
 */
public class PurgeServiceImpl implements InitializingBean  {

    private static final Log LOG = LogFactory.getLog(PurgeServiceImpl.class);

    private FedoraUtility fedoraUtility;

    public void purge(final PurgeRequest purgeRequest) {
        // TODO: Refector this old code.
        try {
                try {
                    boolean isInternalUser = UserContext.isInternalUser();

                    if (!isInternalUser) {
                        UserContext.setUserContext("");
                        UserContext.runAsInternalUser();
                    }
                }
                catch (final Exception e) {
                    UserContext.setUserContext("");
                    UserContext.runAsInternalUser();
                }
                for (final String componentId : TripleStoreUtility.getInstance()
                    .getComponents(purgeRequest.getRequestId())) {
                    this.fedoraUtility.deleteObject(componentId, false);
                }
                this.fedoraUtility.deleteObject(purgeRequest.getRequestId(), false);
                // synchronize triple store
                this.fedoraUtility.sync();

            } catch (final Exception e) {
                LOG.error("could not dequeue message", e);
            } finally {
                // TODO: PurgeStatus ist ein Singleton. Dies funktioniert nicht in einer Cluster-Umgebung. Muss überarbeitet werden.
                // PurgeStatus.getInstance().dec();
            }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // TODO: Dependency Auflösung mit Spring wird hier umgangen. Spring kann somit rekursive Abhängigkeiten nicht auflösen. BeanLocator muss entfernt werden!
            fedoraUtility = (FedoraUtility) BeanLocator.getBean(
                BeanLocator.COMMON_FACTORY_ID,
                "escidoc.core.business.FedoraUtility");
        }
        catch (WebserverSystemException e) {
            LOG.error("could not localize bean", e);
        }
    }
}
