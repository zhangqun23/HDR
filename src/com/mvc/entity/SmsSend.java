package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the sms_send database table.
 * 
 */
@Entity
@Table(name="sms_send")
@NamedQuery(name="SmsSend.findAll", query="SELECT s FROM SmsSend s")
public class SmsSend implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sms_id", unique=true, nullable=false)
	private int smsId;

	@Column(name="guest_flg", nullable=false)
	private int guestFlg;

	@Column(name="send_flag")
	private int sendFlag;

	@Column(name="send_num", nullable=false)
	private int sendNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime;

	@Column(name="sms_create_time", nullable=false)
	private Timestamp smsCreateTime;

	@Column(name="sms_tel", length=16)
	private String smsTel;

	@Lob
	@Column(name="sms_text")
	private String smsText;

	public SmsSend() {
	}

	public int getSmsId() {
		return this.smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public int getGuestFlg() {
		return this.guestFlg;
	}

	public void setGuestFlg(int guestFlg) {
		this.guestFlg = guestFlg;
	}

	public int getSendFlag() {
		return this.sendFlag;
	}

	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}

	public int getSendNum() {
		return this.sendNum;
	}

	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Timestamp getSmsCreateTime() {
		return this.smsCreateTime;
	}

	public void setSmsCreateTime(Timestamp smsCreateTime) {
		this.smsCreateTime = smsCreateTime;
	}

	public String getSmsTel() {
		return this.smsTel;
	}

	public void setSmsTel(String smsTel) {
		this.smsTel = smsTel;
	}

	public String getSmsText() {
		return this.smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

}