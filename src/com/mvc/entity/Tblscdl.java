package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tblscdl database table.
 * 
 */
@Entity
@Table(name="tblscdl")
@NamedQuery(name="Tblscdl.findAll", query="SELECT t FROM Tblscdl t")
public class Tblscdl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="scdl_id", unique=true, nullable=false)
	private int scdlId;

	@Column(name="case_id", length=16)
	private String caseId;

	@Column(name="receiver_ip", length=16)
	private String receiverIp;

	@Column(name="recycle_flag")
	private int recycleFlag;

	@Column(name="recycle_num")
	private int recycleNum;

	@Column(name="recycle_time")
	private int recycleTime;

	@Column(name="scdl_flag")
	private int scdlFlag;

	@Column(name="scdl_msg", length=150)
	private String scdlMsg;

	@Column(name="scdl_oprt", length=20)
	private String scdlOprt;

	@Column(name="scdl_send_mobil", length=20)
	private String scdlSendMobil;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="scdl_time")
	private Date scdlTime;

	@Column(name="scdl_time_list", length=150)
	private String scdlTimeList;

	@Column(name="scdl_type", length=8)
	private String scdlType;

	public Tblscdl() {
	}

	public int getScdlId() {
		return this.scdlId;
	}

	public void setScdlId(int scdlId) {
		this.scdlId = scdlId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getReceiverIp() {
		return this.receiverIp;
	}

	public void setReceiverIp(String receiverIp) {
		this.receiverIp = receiverIp;
	}

	public int getRecycleFlag() {
		return this.recycleFlag;
	}

	public void setRecycleFlag(int recycleFlag) {
		this.recycleFlag = recycleFlag;
	}

	public int getRecycleNum() {
		return this.recycleNum;
	}

	public void setRecycleNum(int recycleNum) {
		this.recycleNum = recycleNum;
	}

	public int getRecycleTime() {
		return this.recycleTime;
	}

	public void setRecycleTime(int recycleTime) {
		this.recycleTime = recycleTime;
	}

	public int getScdlFlag() {
		return this.scdlFlag;
	}

	public void setScdlFlag(int scdlFlag) {
		this.scdlFlag = scdlFlag;
	}

	public String getScdlMsg() {
		return this.scdlMsg;
	}

	public void setScdlMsg(String scdlMsg) {
		this.scdlMsg = scdlMsg;
	}

	public String getScdlOprt() {
		return this.scdlOprt;
	}

	public void setScdlOprt(String scdlOprt) {
		this.scdlOprt = scdlOprt;
	}

	public String getScdlSendMobil() {
		return this.scdlSendMobil;
	}

	public void setScdlSendMobil(String scdlSendMobil) {
		this.scdlSendMobil = scdlSendMobil;
	}

	public Date getScdlTime() {
		return this.scdlTime;
	}

	public void setScdlTime(Date scdlTime) {
		this.scdlTime = scdlTime;
	}

	public String getScdlTimeList() {
		return this.scdlTimeList;
	}

	public void setScdlTimeList(String scdlTimeList) {
		this.scdlTimeList = scdlTimeList;
	}

	public String getScdlType() {
		return this.scdlType;
	}

	public void setScdlType(String scdlType) {
		this.scdlType = scdlType;
	}

}