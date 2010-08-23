package de.escidoc.core.test.tme.jhove;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.escidoc.core.common.exceptions.remote.application.invalid.TmeException;
import de.escidoc.core.common.exceptions.remote.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.remote.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.remote.application.missing.MissingMethodParameterException;
import de.escidoc.core.test.security.client.PWCallback;

public class JhoveTest extends JhoveTestBase {

    public JhoveTest(int transport) {
        super(transport);
    }

    /**
     * Set up servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Clean up after servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Override
    public void tearDown() throws Exception {

        PWCallback.resetHandle();
        super.tearDown();
    }

    /**
     * Test successfully call jhove service to extract technical metadata for an
     * image and a pdf file.
     * 
     * @test.name Extract technical metadata - Success
     * @test.id JHOVE_ETM_1
     * @test.input Valid TME
     * @test.expected: The expected result is the XML representation of the
     *                 jhove extraction result.
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testJhoveEtm1() throws Exception {

        // prepare item with pdf content
        URL file =
            uploadFileToStagingServlet("ges-eSciDoc-article.pdf",
                "application/pdf");

        Document template =
            getTemplateAsDocument(TEMPLATE_TME_PATH, "request.xml");
        Node temp =
            substitute(template, "/request/file[2]/@href", file.toString());

        String request = toString(temp, false);
        String result = extract(request);
        Document resultDoc = getDocument(result);
        selectSingleNodeAsserted(resultDoc, "/jhove");
        assertJhoveRepInfo(resultDoc, "http://localhost:8080/images/escidoc-logo.jpg",
            "JPEG-hul", "1.2", "5304", "JPEG", "1.02", "Well-Formed and valid",
            "JPEG-hul");
        assertJhoveRepInfo(resultDoc, file.toString(), "PDF-hul", "1.7",
            "129361", "PDF", "1.4", "Well-Formed and valid", "PDF-hul");
        assertEquals(
            "Jhove report does not contain expected number of results!", 2,
            selectNodeList(resultDoc, "/jhove/repInfo").getLength());
    }

    public void testJhoveEtm2a() throws Exception {

        Class<XmlSchemaValidationException> ec =
            XmlSchemaValidationException.class;
        try {
            String request =
                "<request xmlns:xlink=\"http://www.w3.org/1999/xlink\"/>";
            extract(request);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    public void testJhoveEtm2b() throws Exception {

        Class<XmlCorruptedException> ec = XmlCorruptedException.class;
        try {
            String request =
                "<request xmlns:xlink=\"http://www.w3.org/1999/xlink\">";
            extract(request);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    public void testJhoveEtm2c() throws Exception {

        Class<XmlCorruptedException> ec = XmlCorruptedException.class;
        try {
            String request = "<requests />";
            extract(request);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    public void testJhoveEtm3a() throws Exception {

        Class<MissingMethodParameterException> ec =
            MissingMethodParameterException.class;
        try {
            extract(null);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    public void testJhoveEtm3b() throws Exception {

        Class<MissingMethodParameterException> ec =
            MissingMethodParameterException.class;
        try {
            String request = "";
            extract(request);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    public void testJhoveEtm4a() throws Exception {

        Class<TmeException> ec = TmeException.class;
        try {
            String request =
                getTemplateAsString(TEMPLATE_TME_PATH,
                    "requestEmptyFileLink.xml");
            extract(request);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    public void testJhoveEtm4b() throws Exception {

        Class<TmeException> ec = TmeException.class;
        try {
            String request =
                getTemplateAsString(TEMPLATE_TME_PATH,
                    "requestInvalidFileLink.xml");
            extract(request);
            failMissingException(ec);
        }
        catch (Exception e) {
            assertExceptionType(ec, e);
        }
    }

    /**
     * Assert JHove report info.
     * 
     * @param node
     * @param uri
     * @param reportingModule
     * @param reportingModuleRelease
     * @param size
     *            File size.
     * @param format
     * @param version
     * @param status
     * @param module
     * @throws Exception
     */
    private void assertJhoveRepInfo(
        final Node node, final String uri, final String reportingModule,
        final String reportingModuleRelease, final String size,
        final String format, final String version, final String status,
        final String module) throws Exception {

        final String repInfoXpath = "/jhove/repInfo[@uri=\"" + uri + "\"]";
        selectSingleNodeAsserted(node, repInfoXpath);

        assertEquals("ReportingModule not as expected! ", reportingModule,
            selectSingleNodeAsserted(
                node,
                repInfoXpath + "/reportingModule[@release=\""
                    + reportingModuleRelease + "\"]").getTextContent());
        assertEquals("Size not as expected! ", size, selectSingleNodeAsserted(
            node, repInfoXpath + "/size").getTextContent());
        assertEquals("Format not as expected! ", format,
            selectSingleNodeAsserted(node, repInfoXpath + "/format")
                .getTextContent());
        assertEquals("Version not as expected! ", version,
            selectSingleNodeAsserted(node, repInfoXpath + "/version")
                .getTextContent());
        assertEquals("Status not as expected! ", status,
            selectSingleNodeAsserted(node, repInfoXpath + "/status")
                .getTextContent());
        assertEquals("module not as expected! ", module,
            selectSingleNodeAsserted(node, repInfoXpath + "/sigMatch/module")
                .getTextContent());
    }

}
