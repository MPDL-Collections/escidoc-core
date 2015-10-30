package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="escidoc_role"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class EscidocRoleBase implements java.io.Serializable {

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
     *             column="role_name"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String roleName;

    /**
     *            @hibernate.property
     *             column="description"
     *             length="2147483647"
     *         
     */
    private String description;

    /**
     *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */
    private Date creationDate;

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
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="role_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    private Set<RoleGrant> roleGrants = new HashSet<RoleGrant>(0);

    /**
     *            @hibernate.bag
     *             lazy="false"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="role_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.ScopeDef"
     *         
     */
    private Collection<ScopeDef> scopeDefs = new ArrayList<ScopeDef>(0);

    /**
     *            @hibernate.bag
     *             lazy="false"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="role_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.EscidocPolicy"
     *         
     */
    private Collection<EscidocPolicy> escidocPolicies = new ArrayList<EscidocPolicy>(0);

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="modified_by_id"         
     *         
     */
    private UserAccount userAccountByModifiedById;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="creator_id"         
     *         
     */
    private UserAccount userAccountByCreatorId;

    public EscidocRoleBase() {
    }

    public EscidocRoleBase(String roleName) {
        this.roleName = roleName;
    }

    public EscidocRoleBase(String roleName, String description, Date creationDate, Date lastModificationDate,
        Set<RoleGrant> roleGrants, Collection<ScopeDef> scopeDefs, Collection<EscidocPolicy> escidocPolicies,
        UserAccount userAccountByModifiedById, UserAccount userAccountByCreatorId) {
        this.roleName = roleName;
        this.description = description;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.roleGrants = roleGrants;
        this.scopeDefs = scopeDefs;
        this.escidocPolicies = escidocPolicies;
        this.userAccountByModifiedById = userAccountByModifiedById;
        this.userAccountByCreatorId = userAccountByCreatorId;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    @GenericGenerator(name = "generator", strategy = "de.escidoc.core.common.persistence.EscidocIdGenerator", parameters = @Parameter(name = "schema", value = "aa"))
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
     *             column="role_name"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "role_name", unique = true, nullable = false)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**       
     *      *            @hibernate.property
     *             column="description"
     *             length="2147483647"
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
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="role_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    @Fetch(value = FetchMode.SUBSELECT)
    public Set<RoleGrant> getRoleGrants() {
        return this.roleGrants;
    }

    public void setRoleGrants(Set<RoleGrant> roleGrants) {
        this.roleGrants = roleGrants;
    }

    /**       
     *      *            @hibernate.bag
     *             lazy="false"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="role_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.ScopeDef"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Collection<ScopeDef> getScopeDefs() {
        return this.scopeDefs;
    }

    public void setScopeDefs(Collection<ScopeDef> scopeDefs) {
        this.scopeDefs = scopeDefs;
    }

    /**       
     *      *            @hibernate.bag
     *             lazy="false"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="role_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.EscidocPolicy"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<EscidocPolicy> getEscidocPolicies() {
        return this.escidocPolicies;
    }

    public void setEscidocPolicies(Collection<EscidocPolicy> escidocPolicies) {
        this.escidocPolicies = escidocPolicies;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="modified_by_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id")
    public UserAccount getUserAccountByModifiedById() {
        return this.userAccountByModifiedById;
    }

    public void setUserAccountByModifiedById(UserAccount userAccountByModifiedById) {
        this.userAccountByModifiedById = userAccountByModifiedById;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="creator_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    public UserAccount getUserAccountByCreatorId() {
        return this.userAccountByCreatorId;
    }

    public void setUserAccountByCreatorId(UserAccount userAccountByCreatorId) {
        this.userAccountByCreatorId = userAccountByCreatorId;
    }

}
