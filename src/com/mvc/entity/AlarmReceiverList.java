package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the alarm_receiver_list database table.
 * 
 */
@Entity
@Table(name="alarm_receiver_list")
@NamedQuery(name="AlarmReceiverList.findAll", query="SELECT a FROM AlarmReceiverList a")
public class AlarmReceiverList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="alarm_flag")
	private int alarmFlag;

	@Column(name="alarm_id", nullable=false, length=255)
	private String alarmId;

	@Column(name="staff_id", length=11)
	private String staffId;

	public AlarmReceiverList() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAlarmFlag() {
		return this.alarmFlag;
	}

	public void setAlarmFlag(int alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public String getAlarmId() {
		return this.alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

}