package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the staff_info database table.
 * 
 */
@Entity
@Table(name="staff_info")
@NamedQuery(name="StaffInfo.findAll", query="SELECT s FROM StaffInfo s")
public class StaffInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int staff_id;

	@Column(name="case_checker", nullable=false)
	private byte caseChecker;

	@Column(length=100)
	private String deviceId;

	private byte is_personal;

	@Column(nullable=false)
	private int isDeleted;

	@Column(nullable=false)
	private int isOnline;

	private int isTest;

	@Temporal(TemporalType.TIMESTAMP)
	private Date last_login_time;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date last_Online_time;

	@Column(length=255)
	private String service_floor;

	@Column(nullable=false)
	private int staff_Browse;

	@Column(nullable=false, length=16)
	private String staff_name;

	@Column(nullable=false, length=16)
	private String staff_no;

	@Column(length=128)
	private String staff_note;

	@Column(nullable=false)
	private int staff_OnDuty;

	@Column(nullable=false, length=20)
	private String staff_pwd;

	@Column(name="staff_right", nullable=false)
	private int staffRight;

	@Column(length=24)
	private String staff_role1;

	@Column(nullable=false, length=15)
	private String staff_tel;

	@Column(name="task_number", nullable=false)
	private int taskNumber;

	@Column(nullable=false, length=15)
	private String used_pda_no;

	public StaffInfo() {
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public byte getCaseChecker() {
		return this.caseChecker;
	}

	public void setCaseChecker(byte caseChecker) {
		this.caseChecker = caseChecker;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public byte getIs_personal() {
		return this.is_personal;
	}

	public void setIs_personal(byte is_personal) {
		this.is_personal = is_personal;
	}

	public int getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public int getIsTest() {
		return this.isTest;
	}

	public void setIsTest(int isTest) {
		this.isTest = isTest;
	}

	public Date getLast_login_time() {
		return this.last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Date getLast_Online_time() {
		return this.last_Online_time;
	}

	public void setLast_Online_time(Date last_Online_time) {
		this.last_Online_time = last_Online_time;
	}

	public String getService_floor() {
		return this.service_floor;
	}

	public void setService_floor(String service_floor) {
		this.service_floor = service_floor;
	}

	public int getStaff_Browse() {
		return this.staff_Browse;
	}

	public void setStaff_Browse(int staff_Browse) {
		this.staff_Browse = staff_Browse;
	}

	public String getStaff_name() {
		return this.staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStaff_no() {
		return this.staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getStaff_note() {
		return this.staff_note;
	}

	public void setStaff_note(String staff_note) {
		this.staff_note = staff_note;
	}

	public int getStaff_OnDuty() {
		return this.staff_OnDuty;
	}

	public void setStaff_OnDuty(int staff_OnDuty) {
		this.staff_OnDuty = staff_OnDuty;
	}

	public String getStaff_pwd() {
		return this.staff_pwd;
	}

	public void setStaff_pwd(String staff_pwd) {
		this.staff_pwd = staff_pwd;
	}

	public int getStaffRight() {
		return this.staffRight;
	}

	public void setStaffRight(int staffRight) {
		this.staffRight = staffRight;
	}

	public String getStaff_role1() {
		return this.staff_role1;
	}

	public void setStaff_role1(String staff_role1) {
		this.staff_role1 = staff_role1;
	}

	public String getStaff_tel() {
		return this.staff_tel;
	}

	public void setStaff_tel(String staff_tel) {
		this.staff_tel = staff_tel;
	}

	public int getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getUsed_pda_no() {
		return this.used_pda_no;
	}

	public void setUsed_pda_no(String used_pda_no) {
		this.used_pda_no = used_pda_no;
	}

}