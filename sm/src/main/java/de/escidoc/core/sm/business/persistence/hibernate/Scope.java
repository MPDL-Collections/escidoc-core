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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="scopes"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "scopes", schema = "sm")
public class Scope implements java.io.Serializable {

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
     *             column="scope_type"
     *             length="20"
     *             not-null="true"
     *         
     */
    private String scopeType;

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
     *            @hibernate.property
     *             column="modified_by_id"
     *             length="255"
     *         
     */
    private String modifiedById;

    /**
     *            @hibernate.property
     *             column="last_modification_date"
     *             length="29"
     *         
     */
    private Date lastModificationDate;

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="scope_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationDefinition"
     *         
     */
    private Set<AggregationDefinition> aggregationDefinitions = new HashSet<AggregationDefinition>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="scope_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.StatisticData"
     *         
     */
    private Set<StatisticData> statisticDatas = new HashSet<StatisticData>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="scope_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.ReportDefinition"
     *         
     */
    private Set<ReportDefinition> reportDefinitions = new HashSet<ReportDefinition>(0);

    public Scope() {
    }

    public Scope(String name, String scopeType) {
        this.name = name;
        this.scopeType = scopeType;
    }

    public Scope(String name, String scopeType, String creatorId, Date creationDate, String modifiedById,
        Date lastModificationDate, Set<AggregationDefinition> aggregationDefinitions,
        Set<StatisticData> statisticDatas, Set<ReportDefinition> reportDefinitions) {
        this.name = name;
        this.scopeType = scopeType;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.modifiedById = modifiedById;
        this.lastModificationDate = lastModificationDate;
        this.aggregationDefinitions = aggregationDefinitions;
        this.statisticDatas = statisticDatas;
        this.reportDefinitions = reportDefinitions;
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
     *             column="scope_type"
     *             length="20"
     *             not-null="true"
     *         
     */

    @Column(name = "scope_type", nullable = false, length = 20)
    public String getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
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
     *      *            @hibernate.property
     *             column="modified_by_id"
     *             length="255"
     *         
     */

    @Column(name = "modified_by_id")
    public String getModifiedById() {
        return this.modifiedById;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    /**       
     *      *            @hibernate.property
     *             column="last_modification_date"
     *             length="29"
     *         
     */

    @Column(name = "last_modification_date", length = 29)
    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="scope_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.AggregationDefinition"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id", updatable = false)
    public Set<AggregationDefinition> getAggregationDefinitions() {
        return this.aggregationDefinitions;
    }

    public void setAggregationDefinitions(Set<AggregationDefinition> aggregationDefinitions) {
        this.aggregationDefinitions = aggregationDefinitions;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="scope_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.StatisticData"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id", updatable = false)
    public Set<StatisticData> getStatisticDatas() {
        return this.statisticDatas;
    }

    public void setStatisticDatas(Set<StatisticData> statisticDatas) {
        this.statisticDatas = statisticDatas;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="scope_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.ReportDefinition"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id", updatable = false)
    public Set<ReportDefinition> getReportDefinitions() {
        return this.reportDefinitions;
    }

    public void setReportDefinitions(Set<ReportDefinition> reportDefinitions) {
        this.reportDefinitions = reportDefinitions;
    }

}
