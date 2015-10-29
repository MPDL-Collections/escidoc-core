package de.escidoc.core.sm.business.persistence.hibernate;

// Generated 26.10.2015 15:32:54 by Hibernate Tools 3.2.2.GA

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="preprocessing_logs"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "preprocessing_logs", schema = "sm")
public class PreprocessingLog implements java.io.Serializable {

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
     *             column="processing_date"
     *             length="13"
     *             not-null="true"
     *         
     */
    private Date processingDate;

    /**
     *            @hibernate.property
     *             column="log_entry"
     *             length="2147483647"
     *         
     */
    private String logEntry;

    /**
     *            @hibernate.property
     *             column="has_error"
     *             length="1"
     *         
     */
    private Boolean hasError;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="aggregation_definition_id"         
     *         
     */
    private AggregationDefinition aggregationDefinition;

    public PreprocessingLog() {
    }

    public PreprocessingLog(Date processingDate) {
        this.processingDate = processingDate;
    }

    public PreprocessingLog(Date processingDate, String logEntry, Boolean hasError,
        AggregationDefinition aggregationDefinition) {
        this.processingDate = processingDate;
        this.logEntry = logEntry;
        this.hasError = hasError;
        this.aggregationDefinition = aggregationDefinition;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="uuid"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    @GenericGenerator(name = "generator", strategy = "uuid", parameters = @Parameter(name = "schema", value = "sm"))
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
     *             column="processing_date"
     *             length="13"
     *             not-null="true"
     *         
     */
    @Column(name = "processing_date", nullable = false, length = 13)
    public Date getProcessingDate() {
        return this.processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    /**       
     *      *            @hibernate.property
     *             column="log_entry"
     *             length="2147483647"
     *         
     */

    @Column(name = "log_entry", length = 2147483647)
    public String getLogEntry() {
        return this.logEntry;
    }

    public void setLogEntry(String logEntry) {
        this.logEntry = logEntry;
    }

    /**       
     *      *            @hibernate.property
     *             column="has_error"
     *             length="1"
     *         
     */

    @Column(name = "has_error", length = 1)
    public Boolean getHasError() {
        return this.hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
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

}
