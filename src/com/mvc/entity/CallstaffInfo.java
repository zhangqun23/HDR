package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the callstaff_info database table.
 * 
 */
@Entity
@Table(name="callstaff_info")
@NamedQuery(name="CallstaffInfo.findAll", query="SELECT c FROM CallstaffInfo c")
public class CallstaffInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="call_id", unique=true, nullable=false, length=16)
	private String callId;

	@Column(name="build_flag", nullable=false)
	private int buildFlag;

	@Lob
	@Column(name="case_description", nullable=false)
	private String caseDescription;

	@Column(nullable=false)
	private int isdeleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_alert_time", nullable=false)
	private Date lastAlertTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time", nullable=false)
	private Date recordTime;

	@Column(name="recorder_id", nullable=false, length=16)
	private String recorderId;

	@Column(name="service_name", nullable=false, length=24)
	private String serviceName;

	public CallstaffInfo() {
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public int getBuildFlag() {
		return this.buildFlag;
	}

	public void setBuildFlag(int buildFlag) {
		this.buildFlag = buildFlag;
	}

	public String getCaseDescription() {
		return this.caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Date getLastAlertTime() {
		return this.lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}