package de.escidoc.core.sm.business.persistence.hibernate;

// Generated 26.10.2015 15:32:54 by Hibernate Tools 3.2.2.GA

import java.util.Date;
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
 *         table="aggregation_definitions"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "aggregation_definitions", schema = "sm")
public class AggregationDefinition implements java.io.Serializable {

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
     *             column="creator_id"
     *             length="255"
     *         
     */
    private String creatorId;

    /**
     *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */
    private Date creationDate;

    /**
     *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_definition_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationStatisticDataSelector"
     *         
     */
    private Set<AggregationStatisticDataSelector> aggregationStatisticDataSelectors =
        new HashSet<AggregationStatisticDataSelector>(0);

    /**
     *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_definition_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTable"
     *         
     */
    private Set<AggregationTable> aggregationTables = new HashSet<AggregationTable>(0);

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="scope_id"         
     *         
     */
    private Scope scope;

    public AggregationDefinition() {
    }

    public AggregationDefinition(String name) {
        this.name = name;
    }

    public AggregationDefinition(String name, String creatorId, Date creationDate,
        Set<AggregationStatisticDataSelector> aggregationStatisticDataSelectors,
        Set<AggregationTable> aggregationTables, Scope scope) {
        this.name = name;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.aggregationStatisticDataSelectors = aggregationStatisticDataSelectors;
        this.aggregationTables = aggregationTables;
        this.scope = scope;
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
     *             column="creator_id"
     *             length="255"
     *         
     */

    @Column(name = "creator_id")
    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**       
     *      *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */

    @Column(name = "creation_date", length = 29)
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_definition_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationStatisticDataSelector"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aggregation_definition_id", updatable = false)
    public Set<AggregationStatisticDataSelector> getAggregationStatisticDataSelectors() {
        return this.aggregationStatisticDataSelectors;
    }

    public void setAggregationStatisticDataSelectors(
        Set<AggregationStatisticDataSelector> aggregationStatisticDataSelectors) {
        this.aggregationStatisticDataSelectors = aggregationStatisticDataSelectors;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="aggregation_definition_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationTable"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aggregation_definition_id", updatable = false)
    public Set<AggregationTable> getAggregationTables() {
        return this.aggregationTables;
    }

    public void setAggregationTables(Set<AggregationTable> aggregationTables) {
        this.aggregationTables = aggregationTables;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="scope_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id")
    public Scope getScope() {
        return this.scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

}
