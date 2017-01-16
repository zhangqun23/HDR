package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rmstat_handle database table.
 * 
 */
@Entity
@Table(name="rmstat_handle")
@NamedQuery(name="RmstatHandle.findAll", query="SELECT r FROM RmstatHandle r")
public class RmstatHandle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(nullable=false, length=60)
	private String rmstat;

	@Column(length=10)
	private String room;

	@Column(name="staff_id", nullable=false)
	private int staffId;

	public RmstatHandle() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRmstat() {
		return this.rmstat;
	}

	public void setRmstat(String rmstat) {
		this.rmstat = rmstat;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

}