package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the alarm_record database table.
 * 
 */
@Entity
@Table(name="alarm_record")
@NamedQuery(name="AlarmRecord.findAll", query="SELECT a FROM AlarmRecord a")
public class AlarmRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="alarm_flag")
	private int alarmFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="alarm_givetime")
	private Date alarmGivetime;

	@Column(name="alarm_id", nullable=false, length=16)
	private String alarmId;

	public AlarmRecord() {
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

	public Date getAlarmGivetime() {
		return this.alarmGivetime;
	}

	public void setAlarmGivetime(Date alarmGivetime) {
		this.alarmGivetime = alarmGivetime;
	}

	public String getAlarmId() {
		return this.alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

}