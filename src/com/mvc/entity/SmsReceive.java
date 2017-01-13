package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sms_receive database table.
 * 
 */
@Entity
@Table(name="sms_receive")
@NamedQuery(name="SmsReceive.findAll", query="SELECT s FROM SmsReceive s")
public class SmsReceive implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int sms_Id;

	@Column(name="read_flag")
	private int readFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="receive_time")
	private Date receiveTime;

	@Column(name="sms_tel", length=16)
	private String smsTel;

	@Column(name="sms_text", length=128)
	private String smsText;

	public SmsReceive() {
	}

	public int getSms_Id() {
		return this.sms_Id;
	}

	public void setSms_Id(int sms_Id) {
		this.sms_Id = sms_Id;
	}

	public int getReadFlag() {
		return this.readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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