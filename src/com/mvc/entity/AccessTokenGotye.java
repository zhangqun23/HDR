package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the access_token_gotye database table.
 * 
 */
@Entity
@Table(name="access_token_gotye")
@NamedQuery(name="AccessTokenGotye.findAll", query="SELECT a FROM AccessTokenGotye a")
public class AccessTokenGotye implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int atId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(nullable=false, length=255)
	private String token;

	@Column(nullable=false, length=10)
	private String type;

	public AccessTokenGotye() {
	}

	public int getAtId() {
		return this.atId;
	}

	public void setAtId(int atId) {
		this.atId = atId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}