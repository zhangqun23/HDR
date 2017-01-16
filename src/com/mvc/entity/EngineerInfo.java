package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the engineer_info database table.
 * 
 */
@Entity
@Table(name="engineer_info")
@NamedQuery(name="EngineerInfo.findAll", query="SELECT e FROM EngineerInfo e")
public class EngineerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="case_id", unique=true, nullable=false, length=16)
	private String caseId;

	@Column(name="call_id", nullable=false, length=16)
	private String callId;

	@Lob
	@Column(name="case_description", nullable=false)
	private String caseDescription;

	@Column(name="case_rank", nullable=false)
	private int caseRank;

	@Lob
	@Column(name="case_reason")
	private String caseReason;

	@Column(name="case_states", nullable=false, length=16)
	private String caseStates;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="close_time")
	private Date closeTime;

	@Column(name="depart_id", length=16)
	private String departId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="estimated_end_time")
	private Date estimatedEndTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="excute_time", nullable=false)
	private Date excuteTime;

	@Column(nullable=false)
	private int flag;

	@Column(name="given_time", nullable=false, length=15)
	private String givenTime;

	@Column(name="is_deleted", nullable=false, length=1)
	private String isDeleted;

	@Column(nullable=false, length=1)
	private String isAlertModel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_alert_time", nullable=false)
	private Date lastAlertTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="open_time", nullable=false)
	private Date openTime;

	@Column(nullable=false)
	private int planFlg;

	@Column(name="recorder_id", nullable=false, length=16)
	private String recorderId;

	@Lob
	@Column(name="refuse_reason")
	private String refuseReason;

	@Column(name="repair_dep_id", length=16)
	private String repairDepId;

	@Column(name="repair_person", length=16)
	private String repairPerson;

	@Column(name="repair_person_name", length=16)
	private String repairPersonName;

	@Column(name="repair_person_tel", length=16)
	private String repairPersonTel;

	@Column(name="service_area", length=16)
	private String serviceArea;

	@Column(name="service_floor", length=25)
	private String serviceFloor;

	@Column(name="sort_id")
	private int sortId;

	public EngineerInfo() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCaseDescription() {
		return this.caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public int getCaseRank() {
		return this.caseRank;
	}

	public void setCaseRank(int caseRank) {
		this.caseRank = caseRank;
	}

	public String getCaseReason() {
		return this.caseReason;
	}

	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}

	public String getCaseStates() {
		return this.caseStates;
	}

	public void setCaseStates(String caseStates) {
		this.caseStates = caseStates;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public Date getEstimatedEndTime() {
		return this.estimatedEndTime;
	}

	public void setEstimatedEndTime(Date estimatedEndTime) {
		this.estimatedEndTime = estimatedEndTime;
	}

	public Date getExcuteTime() {
		return this.excuteTime;
	}

	public void setExcuteTime(Date excuteTime) {
		this.excuteTime = excuteTime;
	}

	public int getFlag() {
		return this.flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsAlertModel() {
		return this.isAlertModel;
	}

	public void setIsAlertModel(String isAlertModel) {
		this.isAlertModel = isAlertModel;
	}

	public Date getLastAlertTime() {
		return this.lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public int getPlanFlg() {
		return this.planFlg;
	}

	public void setPlanFlg(int planFlg) {
		this.planFlg = planFlg;
	}

	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getRepairDepId() {
		return this.repairDepId;
	}

	public void setRepairDepId(String repairDepId) {
		this.repairDepId = repairDepId;
	}

	public String getRepairPerson() {
		return this.repairPerson;
	}

	public void setRepairPerson(String repairPerson) {
		this.repairPerson = repairPerson;
	}

	public String getRepairPersonName() {
		return this.repairPersonName;
	}

	public void setRepairPersonName(String repairPersonName) {
		this.repairPersonName = repairPersonName;
	}

	public String getRepairPersonTel() {
		return this.repairPersonTel;
	}

	public void setRepairPersonTel(String repairPersonTel) {
		this.repairPersonTel = repairPersonTel;
	}

	public String getServiceArea() {
		return this.serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getServiceFloor() {
		return this.serviceFloor;
	}

	public void setServiceFloor(String serviceFloor) {
		this.serviceFloor = serviceFloor;
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

}