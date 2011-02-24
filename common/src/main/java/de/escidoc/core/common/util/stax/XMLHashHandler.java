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
/**
 * 
 */
package de.escidoc.core.common.util.stax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author FRS
 * 
 */
public class XMLHashHandler extends DefaultHandler {

    private StringBuffer string = null;

    private String hash = null;

    @Override
    public final void characters(final char[] ch, final int start, final int length)
        throws SAXException {
        final StringBuilder cb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            cb.append(ch[i + start]);
        }

        final String characters = cb.toString();
        if (characters.trim().length() != 0) {
            // problems with splited character data
            // string.append("#" + characters);
            string.append(characters);
        }
    }

    public final String getHash() {
        return hash;
    }

    @Override
    public final void startDocument() throws SAXException {
        string = new StringBuffer();
        string.append("begin");
    }

    @Override
    public final void startElement(
            final String uri, final String localName, final String qName, final Attributes attributes)
        throws SAXException {

        final String fqName = createFqName(uri, localName, qName);

        string.append('#');
        string.append(fqName);
        final int length = attributes.getLength();
        final SortedMap<String, String> atts = new TreeMap<String, String>();
        for (int i = 0; i < length; i++) {
            final String curQName = attributes.getQName(i);
            final String attName =
                    '{' + attributes.getURI(i) + '}' + attributes.getLocalName(i);
            if (!"xmlns:xml".equalsIgnoreCase(curQName)) {
                atts.put(attName, attributes.getValue(i));
            }
        }
        for (final String name : atts.keySet()) {
            string.append('#');
            string.append(name);
            string.append('=');
            string.append(atts.get(name));
        }
        // mark for begin of element content, either complex or simple
        string.append('#');
    }

    @Override
    public final void endElement(
            final String uri, final String localName, final String qName)
        throws SAXException {
        string.append('#');
        string.append(createFqName(uri, localName, qName));
    }

    @Override
    public final void endDocument() throws SAXException {
        string.append("#end");
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.toString().getBytes());
            hash = new String(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
            throw new SAXException("No such digest algorithm.", e);
        }
    }

    @Override
    public final void error(final SAXParseException e) throws SAXException {
        throw new SAXException(e);
    }

    /**
     * Create full qualified name.
     * 
     * @param uri
     * @param localName
     * @param qName
     * @return full qualified name
     */
    private String createFqName(
        final String uri, final String localName, final String qName) {

        String fqName = '{' + uri + '}';
        if (localName != null && localName.length() > 0) {
            fqName += localName;
        }
        else {
            fqName += qName.split(":")[1];
        }

        return fqName;
    }
}
