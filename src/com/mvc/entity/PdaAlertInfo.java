package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pda_alert_info database table.
 * 
 */
@Entity
@Table(name="pda_alert_info")
@NamedQuery(name="PdaAlertInfo.findAll", query="SELECT p FROM PdaAlertInfo p")
public class PdaAlertInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="alert_msg", length=255)
	private String alertMsg;

	@Column(name="alert_state", nullable=false)
	private int alertState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="alert_time")
	private Date alertTime;

	@Column(name="case_id", length=32)
	private String caseId;

	@Column(name="receiver_id", length=16)
	private String receiverId;

	public PdaAlertInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlertMsg() {
		return this.alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public int getAlertState() {
		return this.alertState;
	}

	public void setAlertState(int alertState) {
		this.alertState = alertState;
	}

	public Date getAlertTime() {
		return this.alertTime;
	}

	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

}