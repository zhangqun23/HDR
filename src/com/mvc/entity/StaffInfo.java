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

	private Integer staff_id;// ID
	private Byte caseChecker;// 任务验收人标志，1：是，0：否
	private String deviceId;// 设备号
	private Byte is_personal;// 是否个人用
	private Integer isDeleted;// 删除标志位，1：删除，0：未删除
	private Integer isOnline;// 在线标志位，1：在线，0：离线，2超时，3：休息
	private Integer isTest;// 是否是测试账号
	private Date last_login_time;// 上一次手机登录时间
	private Date last_Online_time;// 上一次手机在线时间
	private String service_floor;// 登录区域
	private Integer staff_Browse;// 废弃-------
	private String staff_name;// 用户名
	private String staff_no;// 工号
	private String staff_note;// 用户说明
	private Integer staff_OnDuty;// 废弃-------
	private String staff_pwd;// 用户密码
	private Integer staffRight;// 用户权限，0：管理员，1：高级，2：普通
	private String staff_role1;// (用户角色)，废弃-------
	private String staff_tel;// 用户电话
	private Integer taskNumber;// 当前任务数量
	private String used_pda_no;// 手机绑定的PDA号码

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