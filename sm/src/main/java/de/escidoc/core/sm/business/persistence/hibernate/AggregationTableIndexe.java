package de.escidoc.core.sm.business.persistence.hibernate;

// Generated 26.10.2015 15:32:54 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="aggregation_table_indexes"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "aggregation_table_indexes", schema = "sm")
public class AggregationTableIndexe implements java.io.Serializable {

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
     *             column="name"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String name;

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

    /**
     *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_table_index_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTableIndexField"
     *         
     */
    private Set<AggregationTableIndexField> aggregationTableIndexFields = new HashSet<AggregationTableIndexField>(0);

    public AggregationTableIndexe() {
    }

    public AggregationTableIndexe(String name, int listIndex) {
        this.name = name;
        this.listIndex = listIndex;
    }

    public AggregationTableIndexe(String name, int listIndex, AggregationTable aggregationTable,
        Set<AggregationTableIndexField> aggregationTableIndexFields) {
        this.name = name;
        this.listIndex = listIndex;
        this.aggregationTable = aggregationTable;
        this.aggregationTableIndexFields = aggregationTableIndexFields;
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

    /**       
     *      *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_table_index_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTableIndexField"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aggregation_table_index_id", updatable = false)
    @Fetch(value = FetchMode.SUBSELECT)
    public Set<AggregationTableIndexField> getAggregationTableIndexFields() {
        return this.aggregationTableIndexFields;
    }

    public void setAggregationTableIndexFields(Set<AggregationTableIndexField> aggregationTableIndexFields) {
        this.aggregationTableIndexFields = aggregationTableIndexFields;
    }

}
