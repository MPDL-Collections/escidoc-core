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
 * Copyright 2009 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.aa.business.filter;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.joda.time.DateTime;
import org.z3950.zing.cql.CQLParser;
import org.z3950.zing.cql.CQLTermNode;

import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.business.filter.CqlFilter;
import de.escidoc.core.aa.business.persistence.EscidocRole;
import de.escidoc.core.aa.business.persistence.RoleGrant;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;

/**
 * This class parses a CQL filter to filter for eSciDoc roles and translates it
 * into a Hibernate query.
 * 
 * @author SCHE
 */
public class RoleFilter extends CqlFilter {
    /**
     * Parse the given CQL query and create a corresponding Hibernate query to
     * filter for eSciDoc roles from it.
     * 
     * @param query
     *            CQL query
     * @throws InvalidSearchQueryException
     *             thrown if the given search query could not be translated into
     *             a SQL query
     */
    public RoleFilter(final String query) throws InvalidSearchQueryException {
        criteriaMap.put(Constants.DC_IDENTIFIER_URI, new Object[] { COMPARE_EQ,
            "id" });
        criteriaMap.put(TripleStoreUtility.PROP_NAME, new Object[] {
            COMPARE_LIKE, "roleName" });
        criteriaMap.put(TripleStoreUtility.PROP_CREATED_BY_ID, new Object[] {
            COMPARE_EQ, "userAccountByCreatorId.id" });
        criteriaMap.put(TripleStoreUtility.PROP_MODIFIED_BY_ID, new Object[] {
            COMPARE_EQ, "userAccountByModifiedById.id" });
        criteriaMap.put(TripleStoreUtility.PROP_DESCRIPTION, new Object[] {
            COMPARE_LIKE, "roleDescription" });

        propertyNamesMap.put(TripleStoreUtility.PROP_NAME, "roleName");
        propertyNamesMap.put(TripleStoreUtility.PROP_CREATED_BY_ID,
            "userAccountByCreatorId.id");
        propertyNamesMap.put(TripleStoreUtility.PROP_MODIFIED_BY_ID,
            "userAccountByModifiedById.id");
        propertyNamesMap.put(TripleStoreUtility.PROP_DESCRIPTION,
            "roleDescription");
        propertyNamesMap.put(Constants.DC_IDENTIFIER_URI, "id");

        if (query != null) {
            try {
                CQLParser parser = new CQLParser();

                detachedCriteria =
                    DetachedCriteria.forClass(EscidocRole.class, "r");
                detachedCriteria.add(Restrictions.ne("id",
                    EscidocRole.DEFAULT_USER_ROLE_ID));

                Criterion criterion = evaluate(parser.parse(query));

                if (criterion != null) {
                    detachedCriteria.add(criterion);
                }
            }
            catch (Exception e) {
                throw new InvalidSearchQueryException(e);
            }
        }
    }

    /**
     * Evaluate a CQL term node.
     * 
     * @param node
     *            CQL node
     * 
     * @return Hibernate query reflecting the given CQL query
     * @throws InvalidSearchQueryException
     *             thrown if the given search query could not be translated into
     *             a SQL query
     */
    protected Criterion evaluate(final CQLTermNode node)
        throws InvalidSearchQueryException {
        Criterion result = null;
        Object[] parts = criteriaMap.get(node.getIndex());
        String value = node.getTerm();

        if (parts != null) {
            result =
                evaluate(node.getRelation(), (String) parts[1], value,
                    (Integer) (parts[0]) == COMPARE_LIKE);
        }
        else {
            String columnName = node.getIndex();

            if (columnName != null) {
                if (columnName.equals("limited")) {
                    if (Boolean.parseBoolean(value)) {
                        result = Restrictions.isNotEmpty("scopeDefs");
                    }
                    else {
                        result = Restrictions.isEmpty("scopeDefs");
                    }
                }
                else if (columnName.equals("granted")) {
                    DetachedCriteria subQuery =
                        DetachedCriteria.forClass(RoleGrant.class, "rg");

                    subQuery.setProjection(Projections.rowCount());
                    subQuery.add(Restrictions.eqProperty("escidocRole.id",
                        "r.id"));

                    if (Boolean.parseBoolean(value)) {
                        result = Subqueries.lt(0, subQuery);
                    }
                    else {
                        result = Subqueries.eq(0, subQuery);
                    }
                }
                else if (columnName.equals(Constants.FILTER_CREATION_DATE)) {
                    result =
                        evaluate(
                            node.getRelation(),
                            "creationDate",
                            ((value != null) && (value.length() > 0)) ? new Date(
                                new DateTime(value).getMillis())
                                : null, false);
                }
                else {
                    throw new InvalidSearchQueryException(
                        "unknown filter criteria: " + columnName);
                }
            }
        }
        return result;
    }

    /**
     * Get all property names that are allowed as filter criteria for that
     * filter.
     * 
     * @return all property names for that filter
     */
    public Set<String> getPropertyNames() {
        Set<String> result = new TreeSet<String>();

        result.addAll(super.getPropertyNames());
        result.add("limited");
        result.add("granted");
        result.add(Constants.FILTER_CREATION_DATE);
        return result;
    }
}
