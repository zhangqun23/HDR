package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the linen_getrecord database table.
 * 
 */
@Entity
@Table(name="linen_getrecord")
@NamedQuery(name="LinenGetrecord.findAll", query="SELECT l FROM LinenGetrecord l")
public class LinenGetrecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int linenGet_Id;

	@Column(name="confirm_staff", length=10)
	private String confirmStaff;

	@Column(name="confirm_status", nullable=false, length=5)
	private String confirmStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="confirm_time")
	private Date confirmTime;

	@Column(nullable=false)
	private byte is_Deleted;

	@Column(length=20)
	private String linen_Markid;

	@Column(name="linen_type", length=20)
	private String linenType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date linenGet_Time;

	@Column(name="recorder_id", length=5)
	private String recorderId;

	@Column(name="service_floor", length=255)
	private String serviceFloor;

	@Column(length=20)
	private String staff_id;

	public LinenGetrecord() {
	}

	public int getLinenGet_Id() {
		return this.linenGet_Id;
	}

	public void setLinenGet_Id(int linenGet_Id) {
		this.linenGet_Id = linenGet_Id;
	}

	public String getConfirmStaff() {
		return this.confirmStaff;
	}

	public void setConfirmStaff(String confirmStaff) {
		this.confirmStaff = confirmStaff;
	}

	public String getConfirmStatus() {
		return this.confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public byte getIs_Deleted() {
		return this.is_Deleted;
	}

	public void setIs_Deleted(byte is_Deleted) {
		this.is_Deleted = is_Deleted;
	}

	public String getLinen_Markid() {
		return this.linen_Markid;
	}

	public void setLinen_Markid(String linen_Markid) {
		this.linen_Markid = linen_Markid;
	}

	public String getLinenType() {
		return this.linenType;
	}

	public void setLinenType(String linenType) {
		this.linenType = linenType;
	}

	public Date getLinenGet_Time() {
		return this.linenGet_Time;
	}

	public void setLinenGet_Time(Date linenGet_Time) {
		this.linenGet_Time = linenGet_Time;
	}

	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	public String getServiceFloor() {
		return this.serviceFloor;
	}

	public void setServiceFloor(String serviceFloor) {
		this.serviceFloor = serviceFloor;
	}

	public String getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

}