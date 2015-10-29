package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

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
 *         table="user_login_data"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@Entity
@Table(name = "user_login_data", schema = "aa")
public class UserLoginData implements java.io.Serializable {

    /**
     *            @hibernate.id
     *             generator-class="uuid"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    private String id;

    /**
     *            @hibernate.property
     *             column="handle"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String handle;

    /**
     *            @hibernate.property
     *             column="expiryts"
     *             length="19"
     *             not-null="true"
     *         
     */
    private long expiryts;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="user_id"         
     *         
     */
    private UserAccount userAccount;

    public UserLoginData() {
    }

    public UserLoginData(String handle, long expiryts) {
        this.handle = handle;
        this.expiryts = expiryts;
    }

    public UserLoginData(String handle, long expiryts, UserAccount userAccount) {
        this.handle = handle;
        this.expiryts = expiryts;
        this.userAccount = userAccount;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="uuid"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    @GenericGenerator(name = "generator", strategy = "uuid", parameters = @Parameter(name = "schema", value = "aa"))
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
     *             column="handle"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "handle", nullable = false)
    public String getHandle() {
        return this.handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**       
     *      *            @hibernate.property
     *             column="expiryts"
     *             length="19"
     *             not-null="true"
     *         
     */

    @Column(name = "expiryts", nullable = false, length = 19)
    public long getExpiryts() {
        return this.expiryts;
    }

    public void setExpiryts(long expiryts) {
        this.expiryts = expiryts;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="user_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

}
