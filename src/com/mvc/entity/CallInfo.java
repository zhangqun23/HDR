package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the call_info database table.
 * 
 */
@Entity
@Table(name="call_info")
@NamedQuery(name="CallInfo.findAll", query="SELECT c FROM CallInfo c")
public class CallInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="call_id", unique=true, nullable=false, length=16)
	private String callId;

	@Column(name="alert_num")
	private int alertNum;

	@Column(name="build_flag", nullable=false)
	private int buildFlag;

	@Column(name="call_recorder", length=15)
	private String callRecorder;

	@Column(name="call_remark", length=255)
	private String callRemark;

	@Column(name="call_states", length=20)
	private String callStates;

	@Lob
	@Column(name="case_description", nullable=false)
	private String caseDescription;

	@Column(name="crr_name", length=100)
	private String crrName;

	@Column(name="customer_id", length=16)
	private String customerId;

	@Column(name="customer_service_flag")
	private byte customerServiceFlag;

	@Column(name="is_print", nullable=false)
	private int isPrint;

	@Column(nullable=false)
	private int isdeleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_alert_time", nullable=false)
	private Date lastAlertTime;

	@Column(name="pc_id", length=50)
	private String pcId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time", nullable=false)
	private Date recordTime;

	@Column(name="recorder_id", nullable=false, length=16)
	private String recorderId;

	@Column(name="repair_dep_id", length=16)
	private String repairDepId;

	@Column(name="repair_person", length=16)
	private String repairPerson;

	@Column(name="repair_person_name", length=16)
	private String repairPersonName;

	@Column(name="repair_person_tel", length=16)
	private String repairPersonTel;

	@Column(name="room_id", length=16)
	private String roomId;

	@Column(name="service_area", length=100)
	private String serviceArea;

	@Column(name="service_floor", length=25)
	private String serviceFloor;

	@Column(name="service_id")
	private int serviceId;

	@Column(name="service_sort", nullable=false, length=24)
	private String serviceSort;

	public CallInfo() {
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public int getAlertNum() {
		return this.alertNum;
	}

	public void setAlertNum(int alertNum) {
		this.alertNum = alertNum;
	}

	public int getBuildFlag() {
		return this.buildFlag;
	}

	public void setBuildFlag(int buildFlag) {
		this.buildFlag = buildFlag;
	}

	public String getCallRecorder() {
		return this.callRecorder;
	}

	public void setCallRecorder(String callRecorder) {
		this.callRecorder = callRecorder;
	}

	public String getCallRemark() {
		return this.callRemark;
	}

	public void setCallRemark(String callRemark) {
		this.callRemark = callRemark;
	}

	public String getCallStates() {
		return this.callStates;
	}

	public void setCallStates(String callStates) {
		this.callStates = callStates;
	}

	public String getCaseDescription() {
		return this.caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getCrrName() {
		return this.crrName;
	}

	public void setCrrName(String crrName) {
		this.crrName = crrName;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public byte getCustomerServiceFlag() {
		return this.customerServiceFlag;
	}

	public void setCustomerServiceFlag(byte customerServiceFlag) {
		this.customerServiceFlag = customerServiceFlag;
	}

	public int getIsPrint() {
		return this.isPrint;
	}

	public void setIsPrint(int isPrint) {
		this.isPrint = isPrint;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Date getLastAlertTime() {
		return this.lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public String getPcId() {
		return this.pcId;
	}

	public void setPcId(String pcId) {
		this.pcId = pcId;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
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

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceSort() {
		return this.serviceSort;
	}

	public void setServiceSort(String serviceSort) {
		this.serviceSort = serviceSort;
	}

}