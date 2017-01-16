package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the alert_info database table.
 * 
 */
@Entity
@Table(name="alert_info")
@NamedQuery(name="AlertInfo.findAll", query="SELECT a FROM AlertInfo a")
public class AlertInfo implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="alert_flag", nullable=false)
	private int alertFlag;
	
	@Id
	@Column(name="alert_id", nullable=false, length=16)
	private String alertId;

	@Column(name="alert_message", length=255)
	private String alertMessage;

	@Column(name="alert_sort", length=32)
	private String alertSort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="alert_time")
	private Date alertTime;

	@Column(name="given_time", length=32)
	private String givenTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_alert_time", nullable=false)
	private Date lastAlertTime;

	@Column(name="recycle_flag")
	private int recycleFlag;

	@Column(name="recycle_time")
	private int recycleTime;

	public AlertInfo() {
	}

	public int getAlertFlag() {
		return this.alertFlag;
	}

	public void setAlertFlag(int alertFlag) {
		this.alertFlag = alertFlag;
	}

	public String getAlertId() {
		return this.alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public String getAlertMessage() {
		return this.alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getAlertSort() {
		return this.alertSort;
	}

	public void setAlertSort(String alertSort) {
		this.alertSort = alertSort;
	}

	public Date getAlertTime() {
		return this.alertTime;
	}

	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}

	public String getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	public Date getLastAlertTime() {
		return this.lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public int getRecycleFlag() {
		return this.recycleFlag;
	}

	public void setRecycleFlag(int recycleFlag) {
		this.recycleFlag = recycleFlag;
	}

	public int getRecycleTime() {
		return this.recycleTime;
	}

	public void setRecycleTime(int recycleTime) {
		this.recycleTime = recycleTime;
	}

}