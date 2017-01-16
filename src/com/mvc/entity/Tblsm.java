package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tblsms database table.
 * 
 */
@Entity
@Table(name="tblsms")
@NamedQuery(name="Tblsm.findAll", query="SELECT t FROM Tblsm t")
public class Tblsm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sms_id", unique=true, nullable=false)
	private int smsId;

	@Column(name="sms_mobil", length=15)
	private String smsMobil;

	@Column(name="sms_msg", length=255)
	private String smsMsg;

	@Column(name="sms_name", length=20)
	private String smsName;

	@Column(name="sms_oprt", length=20)
	private String smsOprt;

	@Column(name="sms_return", length=10)
	private String smsReturn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sms_time")
	private Date smsTime;

	@Column(name="sms_type", length=16)
	private String smsType;

	public Tblsm() {
	}

	public int getSmsId() {
		return this.smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public String getSmsMobil() {
		return this.smsMobil;
	}

	public void setSmsMobil(String smsMobil) {
		this.smsMobil = smsMobil;
	}

	public String getSmsMsg() {
		return this.smsMsg;
	}

	public void setSmsMsg(String smsMsg) {
		this.smsMsg = smsMsg;
	}

	public String getSmsName() {
		return this.smsName;
	}

	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	public String getSmsOprt() {
		return this.smsOprt;
	}

	public void setSmsOprt(String smsOprt) {
		this.smsOprt = smsOprt;
	}

	public String getSmsReturn() {
		return this.smsReturn;
	}

	public void setSmsReturn(String smsReturn) {
		this.smsReturn = smsReturn;
	}

	public Date getSmsTime() {
		return this.smsTime;
	}

	public void setSmsTime(Date smsTime) {
		this.smsTime = smsTime;
	}

	public String getSmsType() {
		return this.smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

}