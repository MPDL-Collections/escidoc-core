package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="role_grant"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class RoleGrantBase implements java.io.Serializable {

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
     *             column="user_id"
     *             length="255"
     *         
     */
    private String userId;

    /**
     *            @hibernate.property
     *             column="group_id"
     *             length="255"
     *         
     */
    private String groupId;

    /**
     *            @hibernate.property
     *             column="role_id"
     *             length="255"
     *         
     */
    private String roleId;

    /**
     *            @hibernate.property
     *             column="object_id"
     *             length="255"
     *         
     */
    private String objectId;

    /**
     *            @hibernate.property
     *             column="object_title"
     *             length="255"
     *         
     */
    private String objectTitle;

    /**
     *            @hibernate.property
     *             column="object_href"
     *             length="255"
     *         
     */
    private String objectHref;

    /**
     *            @hibernate.property
     *             column="grant_remark"
     *             length="255"
     *         
     */
    private String grantRemark;

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
     *             column="revoker_id"
     *             length="255"
     *         
     */
    private String revokerId;

    /**
     *            @hibernate.property
     *             column="revocation_date"
     *             length="29"
     *         
     */
    private Date revocationDate;

    /**
     *            @hibernate.property
     *             column="revocation_remark"
     *             length="255"
     *         
     */
    private String revocationRemark;

    /**
     *            @hibernate.property
     *             column="granted_from"
     *             length="29"
     *         
     */
    private Timestamp grantedFrom;

    /**
     *            @hibernate.property
     *             column="granted_to"
     *             length="29"
     *         
     */
    private Timestamp grantedTo;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="role_id"         
     *         
     */
    private EscidocRole escidocRole;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="revoker_id"         
     *         
     */
    private UserAccount userAccountByRevokerId;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="user_id"         
     *         
     */
    private UserAccount userAccountByUserId;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="creator_id"         
     *         
     */
    private UserAccount userAccountByCreatorId;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="group_id"         
     *         
     */
    private UserGroup userGroupByGroupId;

    public RoleGrantBase() {
    }

    public RoleGrantBase(String userId, String groupId, String roleId, String objectId, String objectTitle,
        String objectHref, String grantRemark, String creatorId, Date creationDate, String revokerId,
        Date revocationDate, String revocationRemark, Timestamp grantedFrom, Timestamp grantedTo,
        EscidocRole escidocRole, UserAccount userAccountByRevokerId, UserAccount userAccountByUserId,
        UserAccount userAccountByCreatorId, UserGroup userGroupByGroupId) {
        this.userId = userId;
        this.groupId = groupId;
        this.roleId = roleId;
        this.objectId = objectId;
        this.objectTitle = objectTitle;
        this.objectHref = objectHref;
        this.grantRemark = grantRemark;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.revokerId = revokerId;
        this.revocationDate = revocationDate;
        this.revocationRemark = revocationRemark;
        this.grantedFrom = grantedFrom;
        this.grantedTo = grantedTo;
        this.escidocRole = escidocRole;
        this.userAccountByRevokerId = userAccountByRevokerId;
        this.userAccountByUserId = userAccountByUserId;
        this.userAccountByCreatorId = userAccountByCreatorId;
        this.userGroupByGroupId = userGroupByGroupId;
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
     *             column="user_id"
     *             length="255"
     *         
     */

    @Column(name = "user_id", insertable = false, updatable = false)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**       
     *      *            @hibernate.property
     *             column="group_id"
     *             length="255"
     *         
     */

    @Column(name = "group_id", insertable = false, updatable = false)
    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**       
     *      *            @hibernate.property
     *             column="role_id"
     *             length="255"
     *         
     */

    @Column(name = "role_id", insertable = false, updatable = false)
    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**       
     *      *            @hibernate.property
     *             column="object_id"
     *             length="255"
     *         
     */

    @Column(name = "object_id")
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**       
     *      *            @hibernate.property
     *             column="object_title"
     *             length="255"
     *         
     */

    @Column(name = "object_title")
    public String getObjectTitle() {
        return this.objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    /**       
     *      *            @hibernate.property
     *             column="object_href"
     *             length="255"
     *         
     */

    @Column(name = "object_href")
    public String getObjectHref() {
        return this.objectHref;
    }

    public void setObjectHref(String objectHref) {
        this.objectHref = objectHref;
    }

    /**       
     *      *            @hibernate.property
     *             column="grant_remark"
     *             length="255"
     *         
     */

    @Column(name = "grant_remark")
    public String getGrantRemark() {
        return this.grantRemark;
    }

    public void setGrantRemark(String grantRemark) {
        this.grantRemark = grantRemark;
    }

    /**       
     *      *            @hibernate.property
     *             column="creator_id"
     *             length="255"
     *         
     */

    @Column(name = "creator_id", insertable = false, updatable = false)
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
     *             column="revoker_id"
     *             length="255"
     *         
     */

    @Column(name = "revoker_id", insertable = false, updatable = false)
    public String getRevokerId() {
        return this.revokerId;
    }

    public void setRevokerId(String revokerId) {
        this.revokerId = revokerId;
    }

    /**       
     *      *            @hibernate.property
     *             column="revocation_date"
     *             length="29"
     *         
     */

    @Column(name = "revocation_date", length = 29)
    public Date getRevocationDate() {
        return this.revocationDate;
    }

    public void setRevocationDate(Date revocationDate) {
        this.revocationDate = revocationDate;
    }

    /**       
     *      *            @hibernate.property
     *             column="revocation_remark"
     *             length="255"
     *         
     */

    @Column(name = "revocation_remark")
    public String getRevocationRemark() {
        return this.revocationRemark;
    }

    public void setRevocationRemark(String revocationRemark) {
        this.revocationRemark = revocationRemark;
    }

    /**       
     *      *            @hibernate.property
     *             column="granted_from"
     *             length="29"
     *         
     */
    @Column(name = "granted_from", length = 29)
    public Timestamp getGrantedFrom() {
        return this.grantedFrom;
    }

    public void setGrantedFrom(Timestamp grantedFrom) {
        this.grantedFrom = grantedFrom;
    }

    /**       
     *      *            @hibernate.property
     *             column="granted_to"
     *             length="29"
     *         
     */
    @Column(name = "granted_to", length = 29)
    public Timestamp getGrantedTo() {
        return this.grantedTo;
    }

    public void setGrantedTo(Timestamp grantedTo) {
        this.grantedTo = grantedTo;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="role_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    public EscidocRole getEscidocRole() {
        return this.escidocRole;
    }

    public void setEscidocRole(EscidocRole escidocRole) {
        this.escidocRole = escidocRole;
    }

    /**       
     *      *            @hibernate.many-to-one
     *            @hibernate.column name="revoker_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revoker_id")
    public UserAccount getUserAccountByRevokerId() {
        return this.userAccountByRevokerId;
    }

    public void setUserAccountByRevokerId(UserAccount userAccountByRevokerId) {
        this.userAccountByRevokerId = userAccountByRevokerId;
    }

    /**       
     *      *            @hibernate.many-to-one
     *            @hibernate.column name="user_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccountByUserId() {
        return this.userAccountByUserId;
    }

    public void setUserAccountByUserId(UserAccount userAccountByUserId) {
        this.userAccountByUserId = userAccountByUserId;
    }

    /**       
     *      *            @hibernate.many-to-one
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

    /**       
     *      *            @hibernate.many-to-one
     *            @hibernate.column name="group_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    public UserGroup getUserGroupByGroupId() {
        return this.userGroupByGroupId;
    }

    public void setUserGroupByGroupId(UserGroup userGroupByGroupId) {
        this.userGroupByGroupId = userGroupByGroupId;
    }

}
