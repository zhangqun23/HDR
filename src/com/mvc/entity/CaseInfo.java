package com.mvc.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 任务
 * 
 * @author wangrui
 * @date 2016年12月8日
 */
@Entity
@Table(name = "case_info")
public class CaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String caseId;// ID
	private String authorName;// 负责人姓名
	private CallInfo callInfo;// 同call_info中的call_id对应。。。。。。。。。
	private StaffInfo author;// 任务负责人ID，同staff_info中的staff_id对应。。。
	private Integer caseRank;// 任务等级
	private String caseRemark;// 任务备注信息
	private String caseStates;// 任务状态，新建、处理中、申请关闭、关闭
	private String checkAuthorName;// 任务验收人名字
	private String closeReason;// 任务关闭原因
	private Date closeTime;// 任务关闭时间
	private DepartmentInfo departmentInfo;// 该任务隶属于部门的部门ID，同department_info中的department_id对应。。
	private Integer flag;// 报警标志，0：未报警，1、2、3、99代表不同等级的报警
	private Integer givenTime;// 任务给定完成时间，默认单位为分钟
	private Integer isChecked;// 该例行打扫任务是否检查，1：检查，0：未检查
	private Byte isPdaRepair;// 是否是通过PDA报修的
	private Byte isRemind;// 对于送餐服务员是否提醒收餐具
	private Byte isTransmited;// 是否是转交任务，1：是，0：否
	private Integer isAlertModel;// 报警模板
	private Date lastAlertTime;// 上一次报警时间
	private Date openTime;// 任务开始时间
	private Integer planFlg;// 计划任务的标志
	private StaffInfo recorder;// 记录人ID，同staff_info中的staff_id对应。。。
	private String refuseReason;// 任务驳回原因
	private String repairSortId;// 具体报修任务ID，同engineer_case_sort中的sort_id对应
	private String transmitPerson;// 转交任务接收人ID，同staff_info中的staff_id对应

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "case_id", unique = true, nullable = false, length = 16)
	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	@Column(name = "author_name", length = 30)
	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "call_id")
	public CallInfo getCallInfo() {
		return callInfo;
	}

	public void setCallInfo(CallInfo callInfo) {
		this.callInfo = callInfo;
	}

	@ManyToOne
	@JoinColumn(name = "case_author")
	public StaffInfo getAuthor() {
		return author;
	}

	public void setAuthor(StaffInfo author) {
		this.author = author;
	}

	@Column(name = "case_rank", nullable = false)
	public Integer getCaseRank() {
		return this.caseRank;
	}

	public void setCaseRank(Integer caseRank) {
		this.caseRank = caseRank;
	}

	@Column(name = "case_remark", length = 255)
	public String getCaseRemark() {
		return this.caseRemark;
	}

	public void setCaseRemark(String caseRemark) {
		this.caseRemark = caseRemark;
	}

	@Column(name = "case_states", nullable = false, length = 16)
	public String getCaseStates() {
		return this.caseStates;
	}

	public void setCaseStates(String caseStates) {
		this.caseStates = caseStates;
	}

	@Column(name = "check_author_name", length = 30)
	public String getCheckAuthorName() {
		return this.checkAuthorName;
	}

	public void setCheckAuthorName(String checkAuthorName) {
		this.checkAuthorName = checkAuthorName;
	}

	@Column(name = "close_reason", length = 20)
	public String getCloseReason() {
		return this.closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_time")
	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "depart_id")
	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	@Column(nullable = false)
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "given_time", nullable = false)
	public Integer getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(Integer givenTime) {
		this.givenTime = givenTime;
	}

	@Column(name = "is_checked")
	public Integer getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	@Column(name = "is_pda_repair")
	public Byte getIsPdaRepair() {
		return this.isPdaRepair;
	}

	public void setIsPdaRepair(Byte isPdaRepair) {
		this.isPdaRepair = isPdaRepair;
	}

	@Column(name = "is_remind", nullable = false)
	public Byte getIsRemind() {
		return this.isRemind;
	}

	public void setIsRemind(Byte isRemind) {
		this.isRemind = isRemind;
	}

	@Column(name = "is_transmited")
	public Byte getIsTransmited() {
		return this.isTransmited;
	}

	public void setIsTransmited(Byte isTransmited) {
		this.isTransmited = isTransmited;
	}

	@Column(name = "isalertmodel")
	public Integer getIsAlertModel() {
		return this.isAlertModel;
	}

	public void setIsAlertModel(Integer isAlertModel) {
		this.isAlertModel = isAlertModel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_alert_time", nullable = false)
	public Date getLastAlertTime() {
		return this.lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "open_time", nullable = false)
	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@Column(name = "planflg", nullable = false)
	public Integer getPlanFlg() {
		return this.planFlg;
	}

	public void setPlanFlg(Integer planFlg) {
		this.planFlg = planFlg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recorder_id")
	public StaffInfo getRecorder() {
		return recorder;
	}

	public void setRecorder(StaffInfo recorder) {
		this.recorder = recorder;
	}

	@Lob
	@Column(name = "refuse_reason")
	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	@Column(name = "repair_sort_id", length = 16)
	public String getRepairSortId() {
		return this.repairSortId;
	}

	public void setRepairSortId(String repairSortId) {
		this.repairSortId = repairSortId;
	}

	@Column(name = "transmit_person", length = 16)
	public String getTransmitPerson() {
		return this.transmitPerson;
	}

	public void setTransmitPerson(String transmitPerson) {
		this.transmitPerson = transmitPerson;
	}

}