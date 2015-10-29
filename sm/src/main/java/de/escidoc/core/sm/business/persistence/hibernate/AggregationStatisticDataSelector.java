package de.escidoc.core.sm.business.persistence.hibernate;

// Generated 26.10.2015 15:32:54 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="agg_stat_data_selectors"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "agg_stat_data_selectors", schema = "sm")
public class AggregationStatisticDataSelector implements java.io.Serializable {

    /**
     *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    private String id;

    /**
     *            @hibernate.property
     *             column="selector_type"
     *             length="50"
     *             not-null="true"
     *         
     */
    private String selectorType;

    /**
     *            @hibernate.property
     *             column="xpath"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String xpath;

    /**
     *            @hibernate.property
     *             column="list_index"
     *             length="2"
     *             not-null="true"
     *         
     */
    private int listIndex;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="aggregation_definition_id"         
     *         
     */
    private AggregationDefinition aggregationDefinition;

    public AggregationStatisticDataSelector() {
    }

    public AggregationStatisticDataSelector(String selectorType, String xpath, int listIndex) {
        this.selectorType = selectorType;
        this.xpath = xpath;
        this.listIndex = listIndex;
    }

    public AggregationStatisticDataSelector(String selectorType, String xpath, int listIndex,
        AggregationDefinition aggregationDefinition) {
        this.selectorType = selectorType;
        this.xpath = xpath;
        this.listIndex = listIndex;
        this.aggregationDefinition = aggregationDefinition;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    @GenericGenerator(name = "generator", strategy = "de.escidoc.core.common.persistence.EscidocIdGenerator", parameters = @Parameter(name = "schema", value = "sm"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**       
     *      *            @hibernate.property
     *             column="selector_type"
     *             length="50"
     *             not-null="true"
     *         
     */

    @Column(name = "selector_type", nullable = false, length = 50)
    public String getSelectorType() {
        return this.selectorType;
    }

    public void setSelectorType(String selectorType) {
        this.selectorType = selectorType;
    }

    /**       
     *      *            @hibernate.property
     *             column="xpath"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "xpath", nullable = false)
    public String getXpath() {
        return this.xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    /**       
     *      *            @hibernate.property
     *             column="list_index"
     *             length="2"
     *             not-null="true"
     *         
     */

    @Column(name = "list_index", nullable = false, length = 2)
    public int getListIndex() {
        return this.listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="aggregation_definition_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aggregation_definition_id")
    public AggregationDefinition getAggregationDefinition() {
        return this.aggregationDefinition;
    }

    public void setAggregationDefinition(AggregationDefinition aggregationDefinition) {
        this.aggregationDefinition = aggregationDefinition;
    }

}
