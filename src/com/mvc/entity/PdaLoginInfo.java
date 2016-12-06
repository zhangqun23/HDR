package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pda_login_info database table.
 * 
 */
@Entity
@Table(name="pda_login_info")
@NamedQuery(name="PdaLoginInfo.findAll", query="SELECT p FROM PdaLoginInfo p")
public class PdaLoginInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="login_id", unique=true, nullable=false)
	private int loginId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_time")
	private Date loginTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="quit_time")
	private Date quitTime;

	@Column(name="staff_id")
	private int staffId;

	public PdaLoginInfo() {
	}

	public int getLoginId() {
		return this.loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getQuitTime() {
		return this.quitTime;
	}

	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

}