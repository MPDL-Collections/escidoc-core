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
 *         table="aggregation_table_fields"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "aggregation_table_fields", schema = "sm")
public class AggregationTableField implements java.io.Serializable {

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
     *             column="field_type_id"
     *             length="2"
     *             not-null="true"
     *         
     */
    private int fieldTypeId;

    /**
     *            @hibernate.property
     *             column="name"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String name;

    /**
     *            @hibernate.property
     *             column="feed"
     *             length="255"
     *         
     */
    private String feed;

    /**
     *            @hibernate.property
     *             column="xpath"
     *             length="2147483647"
     *         
     */
    private String xpath;

    /**
     *            @hibernate.property
     *             column="data_type"
     *             length="10"
     *         
     */
    private String dataType;

    /**
     *            @hibernate.property
     *             column="reduce_to"
     *             length="255"
     *         
     */
    private String reduceTo;

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
     *            @hibernate.column name="aggregation_table_id"         
     *         
     */
    private AggregationTable aggregationTable;

    public AggregationTableField() {
    }

    public AggregationTableField(int fieldTypeId, String name, int listIndex) {
        this.fieldTypeId = fieldTypeId;
        this.name = name;
        this.listIndex = listIndex;
    }

    public AggregationTableField(int fieldTypeId, String name, String feed, String xpath, String dataType,
        String reduceTo, int listIndex, AggregationTable aggregationTable) {
        this.fieldTypeId = fieldTypeId;
        this.name = name;
        this.feed = feed;
        this.xpath = xpath;
        this.dataType = dataType;
        this.reduceTo = reduceTo;
        this.listIndex = listIndex;
        this.aggregationTable = aggregationTable;
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
     *             column="field_type_id"
     *             length="2"
     *             not-null="true"
     *         
     */

    @Column(name = "field_type_id", nullable = false, length = 2)
    public int getFieldTypeId() {
        return this.fieldTypeId;
    }

    public void setFieldTypeId(int fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    /**       
     *      *            @hibernate.property
     *             column="name"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**       
     *      *            @hibernate.property
     *             column="feed"
     *             length="255"
     *         
     */

    @Column(name = "feed")
    public String getFeed() {
        return this.feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    /**       
     *      *            @hibernate.property
     *             column="xpath"
     *             length="2147483647"
     *         
     */

    @Column(name = "xpath", length = 2147483647)
    public String getXpath() {
        return this.xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    /**       
     *      *            @hibernate.property
     *             column="data_type"
     *             length="10"
     *         
     */

    @Column(name = "data_type", length = 10)
    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**       
     *      *            @hibernate.property
     *             column="reduce_to"
     *             length="255"
     *         
     */

    @Column(name = "reduce_to")
    public String getReduceTo() {
        return this.reduceTo;
    }

    public void setReduceTo(String reduceTo) {
        this.reduceTo = reduceTo;
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
     *            @hibernate.column name="aggregation_table_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aggregation_table_id")
    public AggregationTable getAggregationTable() {
        return this.aggregationTable;
    }

    public void setAggregationTable(AggregationTable aggregationTable) {
        this.aggregationTable = aggregationTable;
    }

}
