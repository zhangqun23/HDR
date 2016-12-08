package com.mvc.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 人员信息表
 * 
 * @author wangrui
 * @date 2016年12月8日
 */
@Entity
@Table(name = "staff_info")
public class StaffInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer staff_id;
	private Byte caseChecker;
	private String deviceId;
	private Byte is_personal;
	private Integer isDeleted;
	private Integer isOnline;
	private Integer isTest;
	private Date last_login_time;
	private Date last_Online_time;
	private String service_floor;
	private Integer staff_Browse;
	private String staff_name;
	private String staff_no;
	private String staff_note;
	private Integer staff_OnDuty;
	private String staff_pwd;
	private Integer staffRight;
	private String staff_role1;
	private String staff_tel;
	private Integer taskNumber;
	private String used_pda_no;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "staff_id", unique = true, nullable = false, length = 16)
	public Integer getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(Integer staff_id) {
		this.staff_id = staff_id;
	}

	@Column(name = "case_checker", nullable = false)
	public Byte getCaseChecker() {
		return this.caseChecker;
	}

	public void setCaseChecker(Byte caseChecker) {
		this.caseChecker = caseChecker;
	}

	@Column(name = "deviceid", length = 100)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "is_personal")
	public Byte getIs_personal() {
		return this.is_personal;
	}

	public void setIs_personal(Byte is_personal) {
		this.is_personal = is_personal;
	}

	@Column(name = "isdeleted", nullable = false)
	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "isonline", nullable = false)
	public Integer getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	@Column(name = "istest")
	public Integer getIsTest() {
		return this.isTest;
	}

	public void setIsTest(Integer isTest) {
		this.isTest = isTest;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	public Date getLast_login_time() {
		return this.last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_online_time", nullable = false)
	public Date getLast_Online_time() {
		return this.last_Online_time;
	}

	public void setLast_Online_time(Date last_Online_time) {
		this.last_Online_time = last_Online_time;
	}

	@Column(length = 255)
	public String getService_floor() {
		return this.service_floor;
	}

	public void setService_floor(String service_floor) {
		this.service_floor = service_floor;
	}

	@Column(name = "staff_browse", nullable = false)
	public Integer getStaff_Browse() {
		return this.staff_Browse;
	}

	public void setStaff_Browse(Integer staff_Browse) {
		this.staff_Browse = staff_Browse;
	}

	@Column(nullable = false, length = 16)
	public String getStaff_name() {
		return this.staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	@Column(nullable = false, length = 16)
	public String getStaff_no() {
		return this.staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	@Column(length = 128)
	public String getStaff_note() {
		return this.staff_note;
	}

	public void setStaff_note(String staff_note) {
		this.staff_note = staff_note;
	}

	@Column(name = "staff_onduty", nullable = false)
	public Integer getStaff_OnDuty() {
		return this.staff_OnDuty;
	}

	public void setStaff_OnDuty(Integer staff_OnDuty) {
		this.staff_OnDuty = staff_OnDuty;
	}

	@Column(nullable = false, length = 20)
	public String getStaff_pwd() {
		return this.staff_pwd;
	}

	public void setStaff_pwd(String staff_pwd) {
		this.staff_pwd = staff_pwd;
	}

	@Column(name = "staff_right", nullable = false)
	public Integer getStaffRight() {
		return this.staffRight;
	}

	public void setStaffRight(Integer staffRight) {
		this.staffRight = staffRight;
	}

	@Column(length = 24)
	public String getStaff_role1() {
		return this.staff_role1;
	}

	public void setStaff_role1(String staff_role1) {
		this.staff_role1 = staff_role1;
	}

	@Column(nullable = false, length = 15)
	public String getStaff_tel() {
		return this.staff_tel;
	}

	public void setStaff_tel(String staff_tel) {
		this.staff_tel = staff_tel;
	}

	@Column(name = "task_number", nullable = false)
	public Integer getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	@Column(nullable = false, length = 15)
	public String getUsed_pda_no() {
		return this.used_pda_no;
	}

	public void setUsed_pda_no(String used_pda_no) {
		this.used_pda_no = used_pda_no;
	}

}