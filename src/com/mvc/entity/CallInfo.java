package com.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 服务信息
 * 
 * @author zjn
 * @date 2016年12月8日
 */
@Entity
@Table(name = "call_info")
public class CallInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "call_id", unique = true, nullable = false, length = 16)
	private String callId;// 主键

	@ManyToOne
	@JoinColumn(name = "service_id")
	private ServiceItem serviceId;// 服务ID，同service_sort中的serviceId对应

	@Column(name = "service_sort", nullable = false, length = 24)
	private String serviceSort;// 服务类型

	@ManyToOne
	@JoinColumn(name = "room_id", referencedColumnName = "room_id")
	private RoomInfo roomId;// 房间号码，同room_info中的room_id对应

	@Column(name = "call_states", length = 20)
	private String callStates;// 当前该任务的状态

	@Column(nullable = false)
	private Integer isdeleted;// 该条是否删除，1删除、0正常

	@Column(name = "alert_num")
	private Integer alertNum;// 报警次数

	@Column(name = "build_flag", nullable = false)
	private Integer buildFlag;// 任务是否建立的标志，1建立、0未建立

	@Column(name = "call_recorder", length = 15)
	private String callRecorder;// 记录人姓名

	@Column(name = "call_remark", length = 255)
	private String callRemark;// 用来保存查房结束之后的有质疑时的质疑结果

	@Lob
	@Column(name = "case_description", nullable = false)
	private String caseDescription;// 任务描述

	@Column(name = "crr_name", length = 100)
	private String crrName;// 该房间号对应的客人姓名

	@Column(name = "customer_id", length = 16)
	private String customerId;// 客人编号，同customer_info中的customer_id对应

	@Column(name = "customer_service_flag")
	private byte customerServiceFlag;// 对客对内的标志，1对客、0对内

	@Column(name = "is_prInteger", nullable = false)
	private Integer isPrInteger;// 是否打印，1打印、0不打印

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_alert_time", nullable = false)
	private Date lastAlertTime;// 上一次提示报警时间

	@Column(name = "pc_id", length = 50)
	private String pcId;// 主要用来和中软做查房任务时，中软发送的主机ID

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "record_time", nullable = false)
	private Date recordTime;// 记录时间

	@Column(name = "recorder_id", nullable = false, length = 16)
	private String recorderId;// 记录人ID，同staff_info中的staff_id对应

	@Column(name = "repair_dep_id", length = 16)
	private String repairDepId;// 报修人部门ID，同staff_dep_rela中的该报修人的Department_id对应

	@Column(name = "repair_person", length = 16)
	private String repairPerson;// 报修人ID，同staff_info中的staff_id对应

	@Column(name = "repair_person_name", length = 16)
	private String repairPersonName;// 报修人名字

	@Column(name = "repair_person_tel", length = 16)
	private String repairPersonTel;// 报修人电话

	@Column(name = "service_area", length = 100)
	private String serviceArea;// 具体区域：（1）房间号（2）公共区域

	@Column(name = "service_floor", length = 25)
	private String serviceFloor;// 服务区域：（1）如果是房间号，则是room_info中该房间对应的room_floor_info（2）如果是公共区域，则对应hotel_area中的area_no的值

	public CallInfo() {
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Integer getAlertNum() {
		return this.alertNum;
	}

	public void setAlertNum(Integer alertNum) {
		this.alertNum = alertNum;
	}

	public Integer getBuildFlag() {
		return this.buildFlag;
	}

	public void setBuildFlag(Integer buildFlag) {
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

	public Integer getIsPrInteger() {
		return this.isPrInteger;
	}

	public void setIsPrInteger(Integer isPrInteger) {
		this.isPrInteger = isPrInteger;
	}

	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
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

	public RoomInfo getRoomId() {
		return this.roomId;
	}

	public void setRoomId(RoomInfo roomId) {
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

	public ServiceItem getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(ServiceItem serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceSort() {
		return this.serviceSort;
	}

	public void setServiceSort(String serviceSort) {
		this.serviceSort = serviceSort;
	}

}