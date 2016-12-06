package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the complain_reciver_list database table.
 * 
 */
@Entity
@Table(name="complain_reciver_list")
@NamedQuery(name="ComplainReciverList.findAll", query="SELECT c FROM ComplainReciverList c")
public class ComplainReciverList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="complain_id", nullable=false, length=16)
	private String complainId;

	@Column(name="confirm_flag")
	private int confirmFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_notify_time")
	private Date lastNotifyTime;

	@Column(name="notify_flag")
	private int notifyFlag;

	@Column(name="receiver_id", nullable=false, length=16)
	private String receiverId;

	public ComplainReciverList() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComplainId() {
		return this.complainId;
	}

	public void setComplainId(String complainId) {
		this.complainId = complainId;
	}

	public int getConfirmFlag() {
		return this.confirmFlag;
	}

	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public Date getLastNotifyTime() {
		return this.lastNotifyTime;
	}

	public void setLastNotifyTime(Date lastNotifyTime) {
		this.lastNotifyTime = lastNotifyTime;
	}

	public int getNotifyFlag() {
		return this.notifyFlag;
	}

	public void setNotifyFlag(int notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

}