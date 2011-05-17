package org.escidoc.core.util.xml.internal;

import net.sf.oval.guard.Guarded;
import org.esidoc.core.utils.io.Datastream;
import org.esidoc.core.utils.xml.DatastreamHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.esidoc.core.utils.Preconditions.checkNotNull;

/**
 * eSciDoc specific implementation of {@link Unmarshaller.Listener}
 *
 * @author <a href="mailto:mail@eduard-hildebrandt.de">Eduard Hildebrandt</a>
 */
@Guarded(applyFieldConstraintsToConstructors = true, applyFieldConstraintsToSetters = true,
        assertParametersNotNull = true, checkInvariants=true, inspectInterfaces = true)
public class EsciDocUnmarshallerListener extends Unmarshaller.Listener {

    public final static Logger LOG = LoggerFactory.getLogger(EsciDocUnmarshallerListener.class);

    private List<UnmarshallerListener> unmarshallerListeners = new ArrayList<UnmarshallerListener>();

    private ElementStreamFilter elementStreamFilter;

    public EsciDocUnmarshallerListener(@NotNull final InputStream inputStream) {
        this.elementStreamFilter = new ElementStreamFilter(inputStream);
        init();
    }

    public EsciDocUnmarshallerListener(XMLStreamReader inputStream) {
        this.elementStreamFilter = new ElementStreamFilter(inputStream);
        init();
    }

    private void init() {
        this.elementStreamFilter.addIgnoredElemenet(
                QName.valueOf("{http://www.escidoc.de/schemas/metadatarecords/0.5}md-record"));
        this.elementStreamFilter.addIgnoredElemenet(
                QName.valueOf("{info:fedora/fedora-system:def/foxml#}xmlContent"));
    }


    public XMLStreamReader getFilteredXmlStreamReader() {
        return this.elementStreamFilter.getFilteredXmlStreamReader();
    }

    public void addUnmarshallerListener(@NotNull final UnmarshallerListener unmarshallerListener) {
        this.unmarshallerListeners.add(unmarshallerListener);
    }

    public void removeUnmarshallerListener(@NotNull final UnmarshallerListener unmarshallerListener) {
        this.unmarshallerListeners.remove(unmarshallerListener);
    }

    public void beforeUnmarshal(@NotNull final Object target, final Object parent) {
        if (target instanceof DatastreamHolder) {
            final DatastreamHolder contentTO = (DatastreamHolder) target;
            beforeUnmarshalContentTO(contentTO);
        }
        for (UnmarshallerListener unmarshallerListener : this.unmarshallerListeners) {
            unmarshallerListener.beforeUnmarshal(target, parent);
        }
    }

    public void afterUnmarshal(@NotNull final Object target, final Object parent) {
        if (target instanceof DatastreamHolder) {
            final DatastreamHolder contentTO = (DatastreamHolder) target;
            afterUnmarshalContentTO(contentTO);
        }
        for (UnmarshallerListener unmarshallerListener : this.unmarshallerListeners) {
            unmarshallerListener.afterUnmarshal(target, parent);
        }
    }

    private void beforeUnmarshalContentTO(final DatastreamHolder datastreamHolder) {
        this.elementStreamFilter.setActive(true);
        this.elementStreamFilter.setOutputStream(datastreamHolder.getDatastream());
    }

    private void afterUnmarshalContentTO(final DatastreamHolder datastreamHolder) {
        this.elementStreamFilter.setActive(false);
        lockStream(datastreamHolder.getDatastream());
    }

    private void lockStream(Datastream stream) {
        try {
            stream.lock();
        } catch (final IOException e) {
            final String errorMessage = "Error on locking stream.";
            if (LOG.isWarnEnabled()) {
                LOG.warn(errorMessage, e);
            }
        }
    }

}