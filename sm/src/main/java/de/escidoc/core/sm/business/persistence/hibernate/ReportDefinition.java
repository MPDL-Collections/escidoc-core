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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="report_definitions"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "report_definitions", schema = "sm")
public class ReportDefinition implements java.io.Serializable {

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
     *             column="sql"
     *             length="2147483647"
     *             not-null="true"
     *         
     */
    private String sql;

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
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="scope_id"         
     *         
     */
    private Scope scope;

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="report_definition_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.ReportDefinitionRole"
     *         
     */
    private Set<ReportDefinitionRole> reportDefinitionRoles = new HashSet<ReportDefinitionRole>(0);

    public ReportDefinition() {
    }

    public ReportDefinition(String name, String sql) {
        this.name = name;
        this.sql = sql;
    }

    public ReportDefinition(String name, String sql, String creatorId, Date creationDate, String modifiedById,
        Date lastModificationDate, Scope scope, Set<ReportDefinitionRole> reportDefinitionRoles) {
        this.name = name;
        this.sql = sql;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.modifiedById = modifiedById;
        this.lastModificationDate = lastModificationDate;
        this.scope = scope;
        this.reportDefinitionRoles = reportDefinitionRoles;
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
     *             column="sql"
     *             length="2147483647"
     *             not-null="true"
     *         
     */

    @Column(name = "sql", nullable = false, length = 2147483647)
    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all"
     *            @hibernate.collection-key
     *             column="report_definition_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.sm.business.persistence.hibernate.ReportDefinitionRole"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "report_definition_id", updatable = false)
    @Fetch(value = FetchMode.SUBSELECT)
    public Set<ReportDefinitionRole> getReportDefinitionRoles() {
        return this.reportDefinitionRoles;
    }

    public void setReportDefinitionRoles(Set<ReportDefinitionRole> reportDefinitionRoles) {
        this.reportDefinitionRoles = reportDefinitionRoles;
    }

}
