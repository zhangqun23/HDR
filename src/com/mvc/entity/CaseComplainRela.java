package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the case_complain_rela database table.
 * 
 */
@Entity
@Table(name="case_complain_rela")
@NamedQuery(name="CaseComplainRela.findAll", query="SELECT c FROM CaseComplainRela c")
public class CaseComplainRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="case_id", unique=true, nullable=false, length=16)
	private String caseId;

	@Column(name="complain_id", length=16)
	private String complainId;

	@Column(name="complain_status")
	private int complainStatus;

	public CaseComplainRela() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getComplainId() {
		return this.complainId;
	}

	public void setComplainId(String complainId) {
		this.complainId = complainId;
	}

	public int getComplainStatus() {
		return this.complainStatus;
	}

	public void setComplainStatus(int complainStatus) {
		this.complainStatus = complainStatus;
	}

}