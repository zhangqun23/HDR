package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the check_case database table.
 * 
 */
@Entity
@Table(name="check_case")
@NamedQuery(name="CheckCase.findAll", query="SELECT c FROM CheckCase c")
public class CheckCase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="check_case_id", unique=true, nullable=false)
	private int checkCaseId;

	@Column(name="author_id", nullable=false, length=16)
	private String authorId;

	@Column(name="case_id", nullable=false, length=16)
	private String caseId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="is_closed", nullable=false)
	private byte isClosed;

	@Column(name="is_transmited", nullable=false)
	private byte isTransmited;

	@Column(name="msg_id")
	private int msgId;

	@Column(name="transmit_person", length=16)
	private String transmitPerson;

	public CheckCase() {
	}

	public int getCheckCaseId() {
		return this.checkCaseId;
	}

	public void setCheckCaseId(int checkCaseId) {
		this.checkCaseId = checkCaseId;
	}

	public String getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getIsClosed() {
		return this.isClosed;
	}

	public void setIsClosed(byte isClosed) {
		this.isClosed = isClosed;
	}

	public byte getIsTransmited() {
		return this.isTransmited;
	}

	public void setIsTransmited(byte isTransmited) {
		this.isTransmited = isTransmited;
	}

	public int getMsgId() {
		return this.msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getTransmitPerson() {
		return this.transmitPerson;
	}

	public void setTransmitPerson(String transmitPerson) {
		this.transmitPerson = transmitPerson;
	}

}