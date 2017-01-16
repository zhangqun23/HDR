package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the manager_staff_record database table.
 * 
 */
@Entity
@Table(name="manager_staff_record")
@NamedQuery(name="ManagerStaffRecord.findAll", query="SELECT m FROM ManagerStaffRecord m")
public class ManagerStaffRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="record_id", unique=true, nullable=false)
	private int recordId;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="manager_id", nullable=false)
	private short managerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time", nullable=false)
	private Date recordTime;

	@Column(name="staff_id", nullable=false)
	private short staffId;

	public ManagerStaffRecord() {
	}

	public int getRecordId() {
		return this.recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public short getManagerId() {
		return this.managerId;
	}

	public void setManagerId(short managerId) {
		this.managerId = managerId;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public short getStaffId() {
		return this.staffId;
	}

	public void setStaffId(short staffId) {
		this.staffId = staffId;
	}

}