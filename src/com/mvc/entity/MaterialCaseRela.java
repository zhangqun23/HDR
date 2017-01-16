package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the material_case_rela database table.
 * 
 */
@Entity
@Table(name="material_case_rela")
@NamedQuery(name="MaterialCaseRela.findAll", query="SELECT m FROM MaterialCaseRela m")
public class MaterialCaseRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="case_id", nullable=false, length=16)
	private String caseId;

	@Column(nullable=false)
	private byte is_deleted;

	@Column(name="material_id", nullable=false)
	private int materialId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="out_time", nullable=false)
	private Date outTime;

	@Column(name="record_id", length=16)
	private String recordId;

	@Column(name="take_amount", nullable=false)
	private int takeAmount;

	@Column(name="take_flag", nullable=false)
	private int takeFlag;

	public MaterialCaseRela() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public byte getIs_deleted() {
		return this.is_deleted;
	}

	public void setIs_deleted(byte is_deleted) {
		this.is_deleted = is_deleted;
	}

	public int getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public Date getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public int getTakeAmount() {
		return this.takeAmount;
	}

	public void setTakeAmount(int takeAmount) {
		this.takeAmount = takeAmount;
	}

	public int getTakeFlag() {
		return this.takeFlag;
	}

	public void setTakeFlag(int takeFlag) {
		this.takeFlag = takeFlag;
	}

}