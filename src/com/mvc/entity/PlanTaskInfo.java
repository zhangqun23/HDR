package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the plan_task_info database table.
 * 
 */
@Entity
@Table(name="plan_task_info")
@NamedQuery(name="PlanTaskInfo.findAll", query="SELECT p FROM PlanTaskInfo p")
public class PlanTaskInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="plan_task_info_id", unique=true, nullable=false)
	private int planTaskInfoId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="close_time", nullable=false)
	private Date closeTime;

	@Column(name="plan_id", nullable=false)
	private int planId;

	@Column(name="room_no", nullable=false, length=16)
	private String roomNo;

	@Column(nullable=false)
	private int staff_id;

	public PlanTaskInfo() {
	}

	public int getPlanTaskInfoId() {
		return this.planTaskInfoId;
	}

	public void setPlanTaskInfoId(int planTaskInfoId) {
		this.planTaskInfoId = planTaskInfoId;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public int getPlanId() {
		return this.planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

}