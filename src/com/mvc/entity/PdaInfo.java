package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pda_info database table.
 * 
 */
@Entity
@Table(name="pda_info")
@NamedQuery(name="PdaInfo.findAll", query="SELECT p FROM PdaInfo p")
public class PdaInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=16)
	private String PDA_id;

	@Column(length=10)
	private String apkVersion;

	@Column(name="login_states", nullable=false)
	private int loginStates;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_time", nullable=false)
	private Date loginTime;

	@Column(length=60)
	private String PDA_deviceId;

	@Column(length=128)
	private String PDA_note;

	@Column(length=16)
	private String PDA_number;

	@Column(length=255)
	private String PDA_shortNumber;

	@Column(length=16)
	private String PDA_states;

	@Column(name="pda_token", length=100)
	private String pdaToken;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="relogin_time")
	private Date reloginTime;

	@Column(name="staff_id", nullable=false)
	private int staffId;

	@Column(name="staff_info", length=255)
	private String staffInfo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public PdaInfo() {
	}

	public String getPDA_id() {
		return this.PDA_id;
	}

	public void setPDA_id(String PDA_id) {
		this.PDA_id = PDA_id;
	}

	public String getApkVersion() {
		return this.apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public int getLoginStates() {
		return this.loginStates;
	}

	public void setLoginStates(int loginStates) {
		this.loginStates = loginStates;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getPDA_deviceId() {
		return this.PDA_deviceId;
	}

	public void setPDA_deviceId(String PDA_deviceId) {
		this.PDA_deviceId = PDA_deviceId;
	}

	public String getPDA_note() {
		return this.PDA_note;
	}

	public void setPDA_note(String PDA_note) {
		this.PDA_note = PDA_note;
	}

	public String getPDA_number() {
		return this.PDA_number;
	}

	public void setPDA_number(String PDA_number) {
		this.PDA_number = PDA_number;
	}

	public String getPDA_shortNumber() {
		return this.PDA_shortNumber;
	}

	public void setPDA_shortNumber(String PDA_shortNumber) {
		this.PDA_shortNumber = PDA_shortNumber;
	}

	public String getPDA_states() {
		return this.PDA_states;
	}

	public void setPDA_states(String PDA_states) {
		this.PDA_states = PDA_states;
	}

	public String getPdaToken() {
		return this.pdaToken;
	}

	public void setPdaToken(String pdaToken) {
		this.pdaToken = pdaToken;
	}

	public Date getReloginTime() {
		return this.reloginTime;
	}

	public void setReloginTime(Date reloginTime) {
		this.reloginTime = reloginTime;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(String staffInfo) {
		this.staffInfo = staffInfo;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}