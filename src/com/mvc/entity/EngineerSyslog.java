package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the engineer_syslog database table.
 * 
 */
@Entity
@Table(name="engineer_syslog")
@NamedQuery(name="EngineerSyslog.findAll", query="SELECT e FROM EngineerSyslog e")
public class EngineerSyslog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int syslog_id;

	@Column(length=16)
	private String staff_id;

	@Lob
	private String syslog_event;

	@Column(length=128)
	private String syslog_note;

	@Temporal(TemporalType.TIMESTAMP)
	private Date syslog_time;

	public EngineerSyslog() {
	}

	public int getSyslog_id() {
		return this.syslog_id;
	}

	public void setSyslog_id(int syslog_id) {
		this.syslog_id = syslog_id;
	}

	public String getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getSyslog_event() {
		return this.syslog_event;
	}

	public void setSyslog_event(String syslog_event) {
		this.syslog_event = syslog_event;
	}

	public String getSyslog_note() {
		return this.syslog_note;
	}

	public void setSyslog_note(String syslog_note) {
		this.syslog_note = syslog_note;
	}

	public Date getSyslog_time() {
		return this.syslog_time;
	}

	public void setSyslog_time(Date syslog_time) {
		this.syslog_time = syslog_time;
	}

}