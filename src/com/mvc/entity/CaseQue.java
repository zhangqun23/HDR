package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the case_ques database table.
 * 
 */
@Entity
@Table(name="case_ques")
@NamedQuery(name="CaseQue.findAll", query="SELECT c FROM CaseQue c")
public class CaseQue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="case_id", unique=true, nullable=false, length=16)
	private String caseId;

	@Lob
	private String remark;

	@Column(name="service_avarage")
	private float serviceAvarage;

	@Column(name="service_kind")
	private float serviceKind;

	@Column(name="service_quality")
	private float serviceQuality;

	@Column(name="service_time")
	private float serviceTime;

	public CaseQue() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public float getServiceAvarage() {
		return this.serviceAvarage;
	}

	public void setServiceAvarage(float serviceAvarage) {
		this.serviceAvarage = serviceAvarage;
	}

	public float getServiceKind() {
		return this.serviceKind;
	}

	public void setServiceKind(float serviceKind) {
		this.serviceKind = serviceKind;
	}

	public float getServiceQuality() {
		return this.serviceQuality;
	}

	public void setServiceQuality(float serviceQuality) {
		this.serviceQuality = serviceQuality;
	}

	public float getServiceTime() {
		return this.serviceTime;
	}

	public void setServiceTime(float serviceTime) {
		this.serviceTime = serviceTime;
	}

}