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
package de.escidoc.core.test.sm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Locale;

import javax.xml.transform.TransformerException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.escidoc.core.common.exceptions.remote.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.remote.application.missing.MissingMethodParameterException;
import de.escidoc.core.test.EscidocAbstractTest;
import de.escidoc.core.test.EscidocTestBase;

/**
 * Test the implementation of the Report resource.
 *
 * @author Michael Hoppe
 */
public class ReportIT extends ReportTestBase {

    private ReportDefinitionIT reportDefinition = null;

    private AggregationDefinitionIT aggregationDefinition = null;

    private static final int aggregationDefinitionsCount = 3;

    private static final int reportDefinitionsCount = 7;

    private static String[][] reportDefinitionIds = new String[aggregationDefinitionsCount][reportDefinitionsCount];

    private static String[] aggregationDefinitionIds = new String[aggregationDefinitionsCount];

    private static int methodCounter = 0;

    private static final String PARAMETER_NAME_XPATH = "/report/report-record/parameter/@name";

    private static final String PARAMETER_VALUE_XPATH = "/report/report-record/parameter/*";

    /**
     * Set up servlet test.
     *
     * @throws Exception If anything fails.
     */
    @Before
    public void initialize() throws Exception {
        reportDefinition = new ReportDefinitionIT();
        aggregationDefinition = new AggregationDefinitionIT();
        if (methodCounter == 0) {
            createAggregationDefinition("escidoc_aggregation_definition3.xml", 0);
            createAggregationDefinition("escidoc_aggregation_definition3_1.xml", 1);
            createAggregationDefinition("escidoc_aggregation_definition3_2.xml", 2);
            createReportDefinitions();
            for (int i = 0; i < aggregationDefinitionIds.length; i++) {
                getPreprocessingClient().triggerPreprocessing(aggregationDefinitionIds[i], "2000-01-01");
            }
        }
    }

    /**
     * Clean up after servlet test.
     *
     * @throws Exception If anything fails.
     */
    @After
    public void deinitialize() throws Exception {
        methodCounter++;
        if (methodCounter == getTestAnnotationsCount()) {
            methodCounter = 0;
            // deleteReportDefinitions();
            // deleteAggregationDefinition();
            // deleteScope();
        }
    }

    /**
     * Creates report-definitions for tests.
     */
    private void createReportDefinitions() {
        try {
            for (int i = 1; i < reportDefinitionsCount + 1; i++) {
                for (int j = 0; j < aggregationDefinitionsCount; j++) {
                    String xml =
                        getTemplateAsFixedReportDefinitionString(TEMPLATE_REP_DEF_PATH,
                            "escidoc_report_definition_for_report_test" + i + ".xml");
                    xml = replaceElementPrimKey(xml, "scope", EscidocTestBase.STATISTIC_SCOPE_ID1);
                    xml = replaceTableNames(xml, aggregationDefinitionIds[j].toString());
                    String result = reportDefinition.create(xml);
                    reportDefinitionIds[j][i - 1] = getPrimKey(result);
                }
            }
        }
        catch (final Exception e) {
            fail("Exception occured " + e.toString());
        }
    }

    /**
     * explain operation without parameters for existing database xyz.
     *
     * @throws Exception If anything fails.
     */
    public void createAggregationDefinition(final String fileName, final int aggregationDefinitionNumber)
        throws Exception {
        String xml = getTemplateAsFixedAggregationDefinitionString(TEMPLATE_AGG_DEF_PATH, fileName);
        xml = replaceElementPrimKey(xml, "scope", EscidocTestBase.STATISTIC_SCOPE_ID1);
        try {
            String result = aggregationDefinition.create(xml);
            aggregationDefinitionIds[aggregationDefinitionNumber] = getPrimKey(result);
        }
        catch (final Exception e) {
            fail("Exception occured " + e.toString());
        }
    }

    /**
     * delete report-definitions.
     *
     * @throws Exception If anything fails.
     */
    public void deleteReportDefinitions() throws Exception {
        for (int i = 0; i < reportDefinitionIds.length; i++) {
            for (int j = 0; j < reportDefinitionIds[i].length; i++) {
                reportDefinition.delete(reportDefinitionIds[i][j].toString());
            }
        }
    }

    /**
     * delete aggregation-definition.
     *
     * @throws Exception If anything fails.
     */
    public void deleteAggregationDefinition() throws Exception {
        for (int i = 0; i < aggregationDefinitionIds.length; i++) {
            aggregationDefinition.delete(aggregationDefinitionIds[i].toString());
        }
    }

    /**
     * retrieve reports with all report-definitionids and compare it to escidoc_expected_reports.
     *
     * @throws Exception If anything fails.
     */
    @Test
    public void testSMRP1() throws Exception {
        StringBuffer results = new StringBuffer("");
        // Always use same report_parameters.xml
        String xml =
            getTemplateAsFixedReportParametersString(TEMPLATE_REP_PARAMETERS_PATH, "escidoc_report_parameters1.xml");

        for (int i = 0; i < aggregationDefinitionIds.length; i++) {
            results.append(checkReport(i, 0, 1, xml));
            results.append(checkReport(i, 1, 2, xml));
            results.append(checkReport(i, 2, 3, xml));
            results.append(checkReport(i, 3, 4, xml));
        }

        // trigger Preprocessing once again/////////////////////////////////////
        for (int i = 0; i < aggregationDefinitionIds.length; i++) {
            getPreprocessingClient().triggerPreprocessing(aggregationDefinitionIds[i], "2009-02-01");
        }
        // /////////////////////////////////////////////////////////////////////

        for (int i = 0; i < aggregationDefinitionIds.length; i++) {
            results.append(checkReport(i, 0, 5, xml));
            results.append(checkReport(i, 1, 6, xml));
            results.append(checkReport(i, 2, 7, xml));
            results.append(checkReport(i, 3, 8, xml));
        }

        // trigger Preprocessing once again/////////////////////////////////////
        for (int i = 0; i < aggregationDefinitionIds.length; i++) {
            getPreprocessingClient().triggerPreprocessing(aggregationDefinitionIds[i], "2000-01-02");
        }
        // /////////////////////////////////////////////////////////////////////

        for (int i = 0; i < aggregationDefinitionIds.length; i++) {
            results.append(checkReport(i, 0, 9, xml));
            results.append(checkReport(i, 1, 10, xml));
            results.append(checkReport(i, 2, 11, xml));
            results.append(checkReport(i, 3, 12, xml));
        }

        StringBuffer assertion = new StringBuffer("");
        for (int i = 0; i < aggregationDefinitionIds.length * 3; i++) {
            for (int j = 0; j < 4; j++) {
                assertion.append("OK");
            }
        }
        assertEquals("results not as expected: " + results.toString(), assertion.toString(), results.toString());
    }

    /**
     * Tests declining retrieving a report with providing corrupted xml.
     *
     * @throws Exception If anything fails.
     */
    @Test
    public void testSMRP2() throws Exception {

        final Class<XmlCorruptedException> ec = XmlCorruptedException.class;
        try {
            retrieve("Corrupted");
            EscidocAbstractTest
                .failMissingException("Retrieving report with providing corrupted xml not declined.", ec);
        }
        catch (final Exception e) {
            EscidocAbstractTest.assertExceptionType("Retrieving report with providing corrupted xml not declined,"
                + " properly.", ec, e);
        }
    }

    /**
     * Tests declining retrieving a report without providing xml.
     *
     * @throws Exception If anything fails.
     */
    @Test
    public void testSMRP3() throws Exception {

        final Class<MissingMethodParameterException> ec = MissingMethodParameterException.class;
        try {
            retrieve(null);
            EscidocAbstractTest.failMissingException("Retrieving report without providing xml not declined.", ec);
        }
        catch (final Exception e) {
            EscidocAbstractTest.assertExceptionType("Retrieving report without providing corrupted xml not declined,"
                + " properly.", ec, e);
        }
    }

    /**
     * Tests retrieving a report with placeholders.
     *
     * @throws Exception If anything fails.
     */
    @Test
    public void testSMRP4() throws Exception {

        final String xml =
            getTemplateAsFixedReportParametersString(TEMPLATE_REP_PARAMETERS_PATH, "escidoc_report_parameters1.xml");

        //Test retrieving report with date and numeric placeholder
        String replacedXml = replaceElementPrimKey(xml, "report-definition", reportDefinitionIds[0][4]);
        retrieve(replacedXml);

        //Test retrieving report with string and numeric placeholder
        replacedXml = replaceElementPrimKey(xml, "report-definition", reportDefinitionIds[0][5]);
        retrieve(replacedXml);

        // check reportDefinition with wrong placeholder////////////////////////
        try {
            replacedXml = replaceElementPrimKey(xml, "report-definition", reportDefinitionIds[0][6]);
            retrieve(replacedXml);
        }
        catch (final Exception e) {
            String exceptionType = e.getClass().getSimpleName();
            assertEquals("MissingMethodParameterException", exceptionType);
        }
    }

    /**
     * retrieve report and check it.
     *
     * @param repDefIndex   index
     * @param expectedIndex index
     * @param xml           report-parameters
     * @return String result
     * @throws Exception If anything fails.
     */
    private String checkReport(final int aggDefIndex, final int repDefIndex, final int expectedIndex, final String xml)
        throws Exception {
        String xml1 =
            replaceElementPrimKey(xml, "report-definition", reportDefinitionIds[aggDefIndex][repDefIndex].toString());
        String result = retrieve(xml1);
        result = result.replaceAll("\\s+", " ").toLowerCase(Locale.ENGLISH);
        Document retrievedDoc = getDocument(result);
        String expected =
            getTemplateAsFixedReportString(TEMPLATE_REPORT_PATH, "escidoc_expected_report" + aggDefIndex + "_"
                + expectedIndex + ".xml");
        if (!result.matches("(?s).*" + reportDefinitionIds[aggDefIndex][repDefIndex].toString() + ".*")) {
            return "WRONG";
        }
        expected = replaceYear(expected, "2009");
        expected = expected.replaceAll("\\s+", " ").toLowerCase(Locale.ENGLISH);
        Document expectedDoc = getDocument(expected);
        if (!isEquals(retrievedDoc, expectedDoc)) {
            return "WRONG";
        }
        else {
            return "OK";
        }
    }

    /**
     * compare values of elements of two different xmls.
     *
     * @param retrieved   retrieved xml
     * @param expected expected xml
     * @return boolean true or false
     * @throws TransformerException If anything fails.
     */
    private boolean isEquals(Node retrieved, Node expected) throws TransformerException {
        NodeList retrievedChilds = selectNodeList(retrieved, PARAMETER_NAME_XPATH);
        NodeList expectedChilds = selectNodeList(expected, PARAMETER_NAME_XPATH);
        for (int i = 0; i < retrievedChilds.getLength(); i++) {
            Node retrievedChild = retrievedChilds.item(i);
            Node expectedChild = expectedChilds.item(i);
            if (!retrievedChild.getTextContent().equals(expectedChild.getTextContent())) {
                return false;
            }
        }
        retrievedChilds = selectNodeList(retrieved, PARAMETER_VALUE_XPATH);
        expectedChilds = selectNodeList(expected, PARAMETER_VALUE_XPATH);
        for (int i = 0; i < retrievedChilds.getLength(); i++) {
            Node retrievedChild = retrievedChilds.item(i);
            Node expectedChild = expectedChilds.item(i);
            if (!retrievedChild.getTextContent().equals(expectedChild.getTextContent())) {
                return false;
            }
        }
        return true;
    }
}
