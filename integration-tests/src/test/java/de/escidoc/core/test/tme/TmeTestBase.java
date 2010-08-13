package de.escidoc.core.test.tme;

import de.escidoc.core.test.EscidocRestSoapTestsBase;
import de.escidoc.core.test.common.client.servlet.tme.JhoveClient;

public class TmeTestBase extends EscidocRestSoapTestsBase {

    private final JhoveClient jhoveClient;

    public TmeTestBase(final int transport) {
        super(transport);
        jhoveClient = new JhoveClient(transport);
    }

    /**
     * @return the jhoveClient
     */
    public JhoveClient getJhoveClient() {
        return jhoveClient;
    }
}
