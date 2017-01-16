package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the complain database table.
 * 
 */
@Entity
@Table(name="complain")
@NamedQuery(name="Complain.findAll", query="SELECT c FROM Complain c")
public class Complain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="complain_id", unique=true, nullable=false, length=16)
	private String complainId;

	@Column(name="alert_flag")
	private byte alertFlag;

	@Column(name="com_recorder", length=10)
	private String comRecorder;

	@Column(name="complain_author")
	private int complainAuthor;

	@Column(name="complain_flag")
	private int complainFlag;

	@Lob
	@Column(name="complain_message")
	private String complainMessage;

	@Lob
	@Column(name="complain_result")
	private String complainResult;

	@Column(name="complain_sort", length=32)
	private String complainSort;

	@Column(name="complain_state", length=11)
	private String complainState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="complain_time")
	private Date complainTime;

	@Column(name="given_time", length=32)
	private String givenTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_alert_time")
	private Date lastAlertTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="open_time")
	private Date openTime;

	@Column(name="recycle_time")
	private int recycleTime;

	public Complain() {
	}

	public String getComplainId() {
		return this.complainId;
	}

	public void setComplainId(String complainId) {
		this.complainId = complainId;
	}

	public byte getAlertFlag() {
		return this.alertFlag;
	}

	public void setAlertFlag(byte alertFlag) {
		this.alertFlag = alertFlag;
	}

	public String getComRecorder() {
		return this.comRecorder;
	}

	public void setComRecorder(String comRecorder) {
		this.comRecorder = comRecorder;
	}

	public int getComplainAuthor() {
		return this.complainAuthor;
	}

	public void setComplainAuthor(int complainAuthor) {
		this.complainAuthor = complainAuthor;
	}

	public int getComplainFlag() {
		return this.complainFlag;
	}

	public void setComplainFlag(int complainFlag) {
		this.complainFlag = complainFlag;
	}

	public String getComplainMessage() {
		return this.complainMessage;
	}

	public void setComplainMessage(String complainMessage) {
		this.complainMessage = complainMessage;
	}

	public String getComplainResult() {
		return this.complainResult;
	}

	public void setComplainResult(String complainResult) {
		this.complainResult = complainResult;
	}

	public String getComplainSort() {
		return this.complainSort;
	}

	public void setComplainSort(String complainSort) {
		this.complainSort = complainSort;
	}

	public String getComplainState() {
		return this.complainState;
	}

	public void setComplainState(String complainState) {
		this.complainState = complainState;
	}

	public Date getComplainTime() {
		return this.complainTime;
	}

	public void setComplainTime(Date complainTime) {
		this.complainTime = complainTime;
	}

	public String getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	public Date getLastAlertTime() {
		return this.lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public int getRecycleTime() {
		return this.recycleTime;
	}

	public void setRecycleTime(int recycleTime) {
		this.recycleTime = recycleTime;
	}

}