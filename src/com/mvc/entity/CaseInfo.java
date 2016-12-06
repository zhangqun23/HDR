package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the case_info database table.
 * 
 */
@Entity
@Table(name="case_info")
@NamedQuery(name="CaseInfo.findAll", query="SELECT c FROM CaseInfo c")
public class CaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="case_id", unique=true, nullable=false, length=16)
	private String caseId;

	@Column(name="author_name", length=30)
	private String authorName;

	@Column(name="call_id", nullable=false, length=16)
	private String callId;

	@Column(name="case_author", nullable=false, length=16)
	private String caseAuthor;

	@Column(name="case_rank", nullable=false)
	private int caseRank;

	@Column(name="case_remark", length=255)
	private String caseRemark;

	@Column(name="case_states", nullable=false, length=16)
	private String caseStates;

	@Column(name="check_author_name", length=30)
	private String checkAuthorName;

	@Column(name="close_reason", length=20)
	private String closeReason;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="close_time")
	private Date closeTime;

	@Column(name="depart_id", length=16)
	private String departId;

	@Column(nullable=false)
	private int flag;

	@Column(name="given_time", nullable=false)
	private int givenTime;

	@Column(name="is_checked")
	private int isChecked;

	@Column(name="is_pda_repair")
	private byte isPdaRepair;

	@Column(name="is_remind", nullable=false)
	private byte isRemind;

	@Column(name="is_transmited")
	private byte isTransmited;

	private int isAlertModel;

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

	@Column(name="repair_sort_id", length=16)
	private String repairSortId;

	@Column(name="transmit_person", length=16)
	private String transmitPerson;

	public CaseInfo() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCaseAuthor() {
		return this.caseAuthor;
	}

	public void setCaseAuthor(String caseAuthor) {
		this.caseAuthor = caseAuthor;
	}

	public int getCaseRank() {
		return this.caseRank;
	}

	public void setCaseRank(int caseRank) {
		this.caseRank = caseRank;
	}

	public String getCaseRemark() {
		return this.caseRemark;
	}

	public void setCaseRemark(String caseRemark) {
		this.caseRemark = caseRemark;
	}

	public String getCaseStates() {
		return this.caseStates;
	}

	public void setCaseStates(String caseStates) {
		this.caseStates = caseStates;
	}

	public String getCheckAuthorName() {
		return this.checkAuthorName;
	}

	public void setCheckAuthorName(String checkAuthorName) {
		this.checkAuthorName = checkAuthorName;
	}

	public String getCloseReason() {
		return this.closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
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

	public int getFlag() {
		return this.flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(int givenTime) {
		this.givenTime = givenTime;
	}

	public int getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public byte getIsPdaRepair() {
		return this.isPdaRepair;
	}

	public void setIsPdaRepair(byte isPdaRepair) {
		this.isPdaRepair = isPdaRepair;
	}

	public byte getIsRemind() {
		return this.isRemind;
	}

	public void setIsRemind(byte isRemind) {
		this.isRemind = isRemind;
	}

	public byte getIsTransmited() {
		return this.isTransmited;
	}

	public void setIsTransmited(byte isTransmited) {
		this.isTransmited = isTransmited;
	}

	public int getIsAlertModel() {
		return this.isAlertModel;
	}

	public void setIsAlertModel(int isAlertModel) {
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

	public String getRepairSortId() {
		return this.repairSortId;
	}

	public void setRepairSortId(String repairSortId) {
		this.repairSortId = repairSortId;
	}

	public String getTransmitPerson() {
		return this.transmitPerson;
	}

	public void setTransmitPerson(String transmitPerson) {
		this.transmitPerson = transmitPerson;
	}

}