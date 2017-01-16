package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the pda_msg database table.
 * 
 */
@Entity
@Table(name="pda_msg")
@NamedQuery(name="PdaMsg.findAll", query="SELECT p FROM PdaMsg p")
public class PdaMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="msg_id", unique=true, nullable=false)
	private int msgId;

	@Column(name="case_id", length=16)
	private String caseId;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

	@Column(nullable=false)
	private int flg;

	@Column(name="is_open")
	private int isOpen;

	@Column(name="is_received")
	private int isReceived;

	@Lob
	@Column(name="msg_info")
	private String msgInfo;

	@Column(name="msg_type", length=16)
	private String msgType;

	@Column(name="send_num", nullable=false)
	private int sendNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime;

	@Column(name="staff_id", length=16)
	private String staffId;

	public PdaMsg() {
	}

	public int getMsgId() {
		return this.msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getFlg() {
		return this.flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}

	public int getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

	public int getIsReceived() {
		return this.isReceived;
	}

	public void setIsReceived(int isReceived) {
		this.isReceived = isReceived;
	}

	public String getMsgInfo() {
		return this.msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
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

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

}