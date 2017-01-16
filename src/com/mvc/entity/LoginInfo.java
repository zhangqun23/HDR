package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the login_info database table.
 * 
 */
@Entity
@Table(name="login_info")
@NamedQuery(name="LoginInfo.findAll", query="SELECT l FROM LoginInfo l")
public class LoginInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=16)
	private String login_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date login_time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date logoff_time;

	@Column(name="PC_IP", length=32)
	private String pcIp;

	@Column(length=128)
	private String remark;

	@Column(name="user_id", length=64)
	private String userId;

	@Column(name="user_pwd", length=16)
	private String userPwd;

	@Column(name="user_state", length=8)
	private String userState;

	public LoginInfo() {
	}

	public String getLogin_id() {
		return this.login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public Date getLogin_time() {
		return this.login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public Date getLogoff_time() {
		return this.logoff_time;
	}

	public void setLogoff_time(Date logoff_time) {
		this.logoff_time = logoff_time;
	}

	public String getPcIp() {
		return this.pcIp;
	}

	public void setPcIp(String pcIp) {
		this.pcIp = pcIp;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

}