package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the alarm_info database table.
 * 
 */
@Entity
@Table(name="alarm_info")
@NamedQuery(name="AlarmInfo.findAll", query="SELECT a FROM AlarmInfo a")
public class AlarmInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="alarm_id", unique=true, nullable=false, length=255)
	private String alarmId;

	@Column(name="alarm_flag")
	private int alarmFlag;

	@Column(name="alarm_level", length=11)
	private String alarmLevel;

	@Column(name="alarm_message", length=255)
	private String alarmMessage;

	@Column(name="alarm_sent")
	private int alarmSent;

	@Column(name="alarm_sort", length=32)
	private String alarmSort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="alarm_time")
	private Date alarmTime;

	@Column(name="receiver_name", length=255)
	private String receiverName;

	public AlarmInfo() {
	}

	public String getAlarmId() {
		return this.alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public int getAlarmFlag() {
		return this.alarmFlag;
	}

	public void setAlarmFlag(int alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAlarmMessage() {
		return this.alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public int getAlarmSent() {
		return this.alarmSent;
	}

	public void setAlarmSent(int alarmSent) {
		this.alarmSent = alarmSent;
	}

	public String getAlarmSort() {
		return this.alarmSort;
	}

	public void setAlarmSort(String alarmSort) {
		this.alarmSort = alarmSort;
	}

	public Date getAlarmTime() {
		return this.alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

}