package org.escidoc.core.services.fedora;

import javax.ws.rs.core.MediaType;

/**
 * @author <a href="mailto:mail@eduard-hildebrandt.de">Eduard Hildebrandt</a>
 */
public final class ListDatastreamsQueryParam {

    private String asOfDateTime;
    private String format = MediaType.TEXT_XML;

    public String getAsOfDateTime() {
        return asOfDateTime;
    }

    public void setAsOfDateTime(final String asOfDateTime) {
        this.asOfDateTime = asOfDateTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }
}
