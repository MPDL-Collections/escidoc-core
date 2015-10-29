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
 *         table="aggregation_table_index_fields"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "aggregation_table_index_fields", schema = "sm")
public class AggregationTableIndexField implements java.io.Serializable {

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
     *             column="field"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String field;

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
     *            @hibernate.column name="aggregation_table_index_id"         
     *         
     */
    private AggregationTableIndexe aggregationTableIndexe;

    public AggregationTableIndexField() {
    }

    public AggregationTableIndexField(String field, int listIndex) {
        this.field = field;
        this.listIndex = listIndex;
    }

    public AggregationTableIndexField(String field, int listIndex, AggregationTableIndexe aggregationTableIndexe) {
        this.field = field;
        this.listIndex = listIndex;
        this.aggregationTableIndexe = aggregationTableIndexe;
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
     *             column="field"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "field", nullable = false)
    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
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
     *            @hibernate.column name="aggregation_table_index_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aggregation_table_index_id")
    public AggregationTableIndexe getAggregationTableIndexe() {
        return this.aggregationTableIndexe;
    }

    public void setAggregationTableIndexe(AggregationTableIndexe aggregationTableIndexe) {
        this.aggregationTableIndexe = aggregationTableIndexe;
    }

}
