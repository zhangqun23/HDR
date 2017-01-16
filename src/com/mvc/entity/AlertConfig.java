package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the alert_config database table.
 * 
 */
@Entity
@Table(name="alert_config")
@NamedQuery(name="AlertConfig.findAll", query="SELECT a FROM AlertConfig a")
public class AlertConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="alert_model_id", unique=true, nullable=false)
	private int alertModelId;

	@Column(name="alert_rank", nullable=false)
	private int alertRank;

	@Column(name="depart_id", nullable=false, length=16)
	private String departId;

	public AlertConfig() {
	}

	public int getAlertModelId() {
		return this.alertModelId;
	}

	public void setAlertModelId(int alertModelId) {
		this.alertModelId = alertModelId;
	}

	public int getAlertRank() {
		return this.alertRank;
	}

	public void setAlertRank(int alertRank) {
		this.alertRank = alertRank;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

}