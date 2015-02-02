package de.escidoc.core.common.util.xml.transformer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import de.escidoc.core.common.business.fedora.FedoraUtility;
import de.escidoc.core.common.exceptions.system.FedoraSystemException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;

public class XmlUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * Utility method for getting the document from Fedora. This method is called from a sylesheet 
     * that enriches the content of the DC stream by dc attributes for Open Aire.
     * 
     * @param componentId
     *            
     * @return Node
     */
    public static Node getXmlDocumentWithAuthentication(String componentId) {

        LOGGER.debug("XmlUtil.getXmlDocumentWithAuthentication with escidocId <" + componentId + ">");

        Node doc = null;
        String url = getFedoraUrl(componentId);

        try {
            InputStream is = new BufferedInputStream(FedoraUtility.getInstance().requestFedoraURL(url));
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            dfactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = dfactory.newDocumentBuilder();

            doc = docBuilder.parse(is);
        }

        catch (IOException e) {
            LOGGER.warn("ERROR when reading foxml of <" + componentId + ">");
            e.printStackTrace();
        }
        catch (SAXException e) {
            LOGGER.warn("ERROR when creating DOM of <" + componentId + ">");
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            LOGGER.warn("ERROR when initializing DOM parser  for <" + componentId + ">");
            e.printStackTrace();
        }
        catch (WebserverSystemException e) {
            LOGGER.warn("ERROR when reading foxml of <" + componentId + ">");
            e.printStackTrace();
        }
        catch (FedoraSystemException e) {
            LOGGER.warn("ERROR when reading foxml of <" + componentId + ">");
            e.printStackTrace();
        }

        return doc;
    }

    private static String getFedoraUrl(String fedoraId) {
        StringBuffer b = new StringBuffer();

        b.append("/objects/");
        b.append(fedoraId);
        b.append("/objectXML");

        return b.toString();
    }

}
