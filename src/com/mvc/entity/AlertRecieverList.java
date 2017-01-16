package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the alert_reciever_list database table.
 * 
 */
@Entity
@Table(name="alert_reciever_list")
@NamedQuery(name="AlertRecieverList.findAll", query="SELECT a FROM AlertRecieverList a")
public class AlertRecieverList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="alert_reciver_id", unique=true, nullable=false)
	private int alertReciverId;

	@Column(name="alert_id", nullable=false, length=16)
	private String alertId;

	@Column(name="case_info_id", length=16)
	private String caseInfoId;

	@Column(name="confirm_flag", nullable=false)
	private int confirmFlag;

	@Column(name="is_transmited")
	private byte isTransmited;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_notify_time", nullable=false)
	private Date lastNotifyTime;

	@Column(name="notify_flag", nullable=false)
	private int notifyFlag;

	@Column(name="receiver_id", nullable=false, length=16)
	private String receiverId;

	@Column(name="transmit_person", length=16)
	private String transmitPerson;

	public AlertRecieverList() {
	}

	public int getAlertReciverId() {
		return this.alertReciverId;
	}

	public void setAlertReciverId(int alertReciverId) {
		this.alertReciverId = alertReciverId;
	}

	public String getAlertId() {
		return this.alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public String getCaseInfoId() {
		return this.caseInfoId;
	}

	public void setCaseInfoId(String caseInfoId) {
		this.caseInfoId = caseInfoId;
	}

	public int getConfirmFlag() {
		return this.confirmFlag;
	}

	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public byte getIsTransmited() {
		return this.isTransmited;
	}

	public void setIsTransmited(byte isTransmited) {
		this.isTransmited = isTransmited;
	}

	public Date getLastNotifyTime() {
		return this.lastNotifyTime;
	}

	public void setLastNotifyTime(Date lastNotifyTime) {
		this.lastNotifyTime = lastNotifyTime;
	}

	public int getNotifyFlag() {
		return this.notifyFlag;
	}

	public void setNotifyFlag(int notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getTransmitPerson() {
		return this.transmitPerson;
	}

	public void setTransmitPerson(String transmitPerson) {
		this.transmitPerson = transmitPerson;
	}

}