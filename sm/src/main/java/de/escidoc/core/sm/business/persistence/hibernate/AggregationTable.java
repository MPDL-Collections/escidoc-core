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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="aggregation_tables"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "aggregation_tables", schema = "sm")
public class AggregationTable implements java.io.Serializable {

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
     *            @hibernate.column name="aggregation_definition_id"         
     *         
     */
    private AggregationDefinition aggregationDefinition;

    /**
     *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_table_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTableField"
     *         
     */
    private Set<AggregationTableField> aggregationTableFields = new HashSet<AggregationTableField>(0);

    /**
     *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_table_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTableIndexe"
     *         
     */
    private Set<AggregationTableIndexe> aggregationTableIndexes = new HashSet<AggregationTableIndexe>(0);

    public AggregationTable() {
    }

    public AggregationTable(String name, int listIndex) {
        this.name = name;
        this.listIndex = listIndex;
    }

    public AggregationTable(String name, int listIndex, AggregationDefinition aggregationDefinition,
        Set<AggregationTableField> aggregationTableFields, Set<AggregationTableIndexe> aggregationTableIndexes) {
        this.name = name;
        this.listIndex = listIndex;
        this.aggregationDefinition = aggregationDefinition;
        this.aggregationTableFields = aggregationTableFields;
        this.aggregationTableIndexes = aggregationTableIndexes;
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

    /**       
     *      *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_table_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTableField"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aggregation_table_id", updatable = false)
    public Set<AggregationTableField> getAggregationTableFields() {
        return this.aggregationTableFields;
    }

    public void setAggregationTableFields(Set<AggregationTableField> aggregationTableFields) {
        this.aggregationTableFields = aggregationTableFields;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_table_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTableIndexe"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aggregation_table_id", updatable = false)
    public Set<AggregationTableIndexe> getAggregationTableIndexes() {
        return this.aggregationTableIndexes;
    }

    public void setAggregationTableIndexes(Set<AggregationTableIndexe> aggregationTableIndexes) {
        this.aggregationTableIndexes = aggregationTableIndexes;
    }

}
