package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mt_oa_interface database table.
 * 
 */
@Entity
@Table(name="mt_oa_interface")
@NamedQuery(name="MtOaInterface.findAll", query="SELECT m FROM MtOaInterface m")
public class MtOaInterface implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="record_id", unique=true, nullable=false)
	private int recordId;

	@Lob
	@Column(name="case_description", nullable=false)
	private String caseDescription;

	@Column(name="case_id", nullable=false, length=16)
	private String caseId;

	@Column(name="case_sort", nullable=false, length=50)
	private String caseSort;

	@Column(name="material_id", nullable=false)
	private int materialId;

	@Column(name="oaupdate_flag")
	private byte oaupdateFlag;

	@Column(name="read_flag", nullable=false)
	private byte readFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time", nullable=false)
	private Date recordTime;

	@Column(name="room_id", length=20)
	private String roomId;

	@Column(name="staff_name", nullable=false, length=20)
	private String staffName;

	@Column(name="staff_no", nullable=false, length=20)
	private String staffNo;

	@Column(name="take_amount", nullable=false)
	private int takeAmount;

	public MtOaInterface() {
	}

	public int getRecordId() {
		return this.recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getCaseDescription() {
		return this.caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSort() {
		return this.caseSort;
	}

	public void setCaseSort(String caseSort) {
		this.caseSort = caseSort;
	}

	public int getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public byte getOaupdateFlag() {
		return this.oaupdateFlag;
	}

	public void setOaupdateFlag(byte oaupdateFlag) {
		this.oaupdateFlag = oaupdateFlag;
	}

	public byte getReadFlag() {
		return this.readFlag;
	}

	public void setReadFlag(byte readFlag) {
		this.readFlag = readFlag;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public int getTakeAmount() {
		return this.takeAmount;
	}

	public void setTakeAmount(int takeAmount) {
		this.takeAmount = takeAmount;
	}

}