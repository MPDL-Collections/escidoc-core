package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="unsecured_action_list"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@Entity
@Table(name = "unsecured_action_list", schema = "aa")
public class UnsecuredActionList implements java.io.Serializable {

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
     *             column="context_id"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String contextId;

    /**
     *            @hibernate.property
     *             column="action_ids"
     *             length="2147483647"
     *             not-null="true"
     *         
     */
    private String actionIds;

    public UnsecuredActionList() {
    }

    public UnsecuredActionList(String contextId, String actionIds) {
        this.contextId = contextId;
        this.actionIds = actionIds;
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
     *             column="context_id"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "context_id", nullable = false)
    public String getContextId() {
        return this.contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    /**       
     *      *            @hibernate.property
     *             column="action_ids"
     *             length="2147483647"
     *             not-null="true"
     *         
     */

    @Column(name = "action_ids", nullable = false, length = 2147483647)
    public String getActionIds() {
        return this.actionIds;
    }

    public void setActionIds(String actionIds) {
        this.actionIds = actionIds;
    }

}
