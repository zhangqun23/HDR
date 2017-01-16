package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the temp_sms_record database table.
 * 
 */
@Entity
@Table(name="temp_sms_record")
@NamedQuery(name="TempSmsRecord.findAll", query="SELECT t FROM TempSmsRecord t")
public class TempSmsRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sms_id", unique=true, nullable=false)
	private int smsId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="recorder_id", nullable=false)
	private int recorderId;

	@Column(name="sms_tel", length=11)
	private String smsTel;

	@Column(name="sms_text", nullable=false, length=255)
	private String smsText;

	public TempSmsRecord() {
	}

	public int getSmsId() {
		return this.smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(int recorderId) {
		this.recorderId = recorderId;
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