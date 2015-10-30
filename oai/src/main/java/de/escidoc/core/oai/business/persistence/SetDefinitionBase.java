package de.escidoc.core.oai.business.persistence;

// Generated 26.10.2015 15:29:23 by Hibernate Tools 3.2.2.GA

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="set_definition"
 *        @hibernate.mapping
 *         schema="oai"
 *     
 */
@MappedSuperclass
public class SetDefinitionBase implements java.io.Serializable {

    /**
     *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *            @hibernate.generator-param
     * 	        name="sequence"
     * 	        value=""
     *         
     */
    private String id;

    /**
     *            @hibernate.property
     *             column="specification"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String specification;

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
     *             column="query"
     *             length="2147483647"
     *             not-null="true"
     *         
     */
    private String query;

    /**
     *            @hibernate.property
     *             column="description"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
    private String description;

    /**
     *            @hibernate.property
     *             column="creator_id"
     *             length="255"
     *         
     */
    private String creatorId;

    /**
     *            @hibernate.property
     *             column="creator_title"
     *             length="255"
     *         
     */
    private String creatorTitle;

    /**
     *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */
    private Timestamp creationDate;

    /**
     *            @hibernate.property
     *             column="modified_by_id"
     *             length="255"
     *         
     */
    private String modifiedById;

    /**
     *            @hibernate.property
     *             column="modified_by_title"
     *             length="255"
     *         
     */
    private String modifiedByTitle;

    /**
     *            @hibernate.property
     *             column="last_modification_date"
     *             length="29"
     *         
     */
    private Timestamp lastModificationDate;

    public SetDefinitionBase() {
    }

    public SetDefinitionBase(String specification, String name, String query) {
        this.specification = specification;
        this.name = name;
        this.query = query;
    }

    public SetDefinitionBase(String specification, String name, String query, String description, String creatorId,
        String creatorTitle, Timestamp creationDate, String modifiedById, String modifiedByTitle,
        Timestamp lastModificationDate) {
        this.specification = specification;
        this.name = name;
        this.query = query;
        this.description = description;
        this.creatorId = creatorId;
        this.creatorTitle = creatorTitle;
        this.creationDate = creationDate;
        this.modifiedById = modifiedById;
        this.modifiedByTitle = modifiedByTitle;
        this.lastModificationDate = lastModificationDate;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *            @hibernate.generator-param
     * 	        name="sequence"
     * 	        value=""
     *         
     */
    @GenericGenerator(name = "generator", strategy = "de.escidoc.core.common.persistence.EscidocIdGenerator", parameters = {
        @Parameter(name = "schema", value = "oai"), @Parameter(name = "sequence", value = "") })
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
     *             column="specification"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "specification", unique = true, nullable = false)
    public String getSpecification() {
        return this.specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
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
     *             column="query"
     *             length="2147483647"
     *             not-null="true"
     *         
     */

    @Column(name = "query", nullable = false, length = 2147483647)
    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    /**       
     *      *            @hibernate.property
     *             column="description"
     *             length="2147483647"
     *             not-null="false"
     *         
     */

    @Column(name = "description", length = 2147483647)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
     *             column="creator_title"
     *             length="255"
     *         
     */

    @Column(name = "creator_title")
    public String getCreatorTitle() {
        return this.creatorTitle;
    }

    public void setCreatorTitle(String creatorTitle) {
        this.creatorTitle = creatorTitle;
    }

    /**       
     *      *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */

    @Column(name = "creation_date", length = 29)
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
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
     *             column="modified_by_title"
     *             length="255"
     *         
     */

    @Column(name = "modified_by_title")
    public String getModifiedByTitle() {
        return this.modifiedByTitle;
    }

    public void setModifiedByTitle(String modifiedByTitle) {
        this.modifiedByTitle = modifiedByTitle;
    }

    /**       
     *      *            @hibernate.property
     *             column="last_modification_date"
     *             length="29"
     *         
     */

    @Column(name = "last_modification_date", length = 29)
    public Timestamp getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Timestamp lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

}
