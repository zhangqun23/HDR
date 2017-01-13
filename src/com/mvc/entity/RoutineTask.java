package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the routine_task database table.
 * 
 */
@Entity
@Table(name="routine_task")
@NamedQuery(name="RoutineTask.findAll", query="SELECT r FROM RoutineTask r")
public class RoutineTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="case_id", unique=true, nullable=false, length=17)
	private String caseId;

	@Column(name="case_author", nullable=false, length=16)
	private String caseAuthor;

	@Column(name="case_sort", length=10)
	private String caseSort;

	@Column(name="case_states", length=10)
	private String caseStates;

	@Column(name="change_rmst", nullable=false)
	private int changeRmst;

	@Column(name="checked_person", nullable=false, length=16)
	private String checkedPerson;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="close_time")
	private Date closeTime;

	@Column(name="is_checked", nullable=false)
	private byte isChecked;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="open_time", nullable=false)
	private Date openTime;

	@Lob
	private String problems;

	@Lob
	@Column(name="refuse_reason")
	private String refuseReason;

	@Column(nullable=false)
	private int refuseNum;

	@Column(name="room_number", length=5)
	private String roomNumber;

	@Lob
	private String things;

	public RoutineTask() {
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseAuthor() {
		return this.caseAuthor;
	}

	public void setCaseAuthor(String caseAuthor) {
		this.caseAuthor = caseAuthor;
	}

	public String getCaseSort() {
		return this.caseSort;
	}

	public void setCaseSort(String caseSort) {
		this.caseSort = caseSort;
	}

	public String getCaseStates() {
		return this.caseStates;
	}

	public void setCaseStates(String caseStates) {
		this.caseStates = caseStates;
	}

	public int getChangeRmst() {
		return this.changeRmst;
	}

	public void setChangeRmst(int changeRmst) {
		this.changeRmst = changeRmst;
	}

	public String getCheckedPerson() {
		return this.checkedPerson;
	}

	public void setCheckedPerson(String checkedPerson) {
		this.checkedPerson = checkedPerson;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public byte getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(byte isChecked) {
		this.isChecked = isChecked;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public String getProblems() {
		return this.problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public int getRefuseNum() {
		return this.refuseNum;
	}

	public void setRefuseNum(int refuseNum) {
		this.refuseNum = refuseNum;
	}

	public String getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getThings() {
		return this.things;
	}

	public void setThings(String things) {
		this.things = things;
	}

}