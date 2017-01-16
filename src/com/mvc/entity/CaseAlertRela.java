package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the case_alert_rela database table.
 * 
 */
@Entity
@Table(name = "case_alert_rela")
@NamedQuery(name = "CaseAlertRela.findAll", query = "SELECT c FROM CaseAlertRela c")
public class CaseAlertRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "alert_id", nullable = false, length = 16)
	private String alertId;

	@Column(name = "alert_status")
	private int alertStatus;

	@Column(name = "case_id", nullable = false, length = 16)
	private String caseId;

	public CaseAlertRela() {
	}

	public String getAlertId() {
		return this.alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public int getAlertStatus() {
		return this.alertStatus;
	}

	public void setAlertStatus(int alertStatus) {
		this.alertStatus = alertStatus;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}