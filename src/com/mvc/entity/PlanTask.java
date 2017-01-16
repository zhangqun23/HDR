package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the plan_task database table.
 * 
 */
@Entity
@Table(name="plan_task")
@NamedQuery(name="PlanTask.findAll", query="SELECT p FROM PlanTask p")
public class PlanTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="plan_id", unique=true, nullable=false)
	private int planId;

	@Temporal(TemporalType.DATE)
	@Column(name="beg_date", nullable=false)
	private Date begDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(nullable=false)
	private int isdeleted;

	@Temporal(TemporalType.DATE)
	@Column(name="over_date", nullable=false)
	private Date overDate;

	@Column(nullable=false)
	private int staff_id;

	@Column(name="task_info", length=255)
	private String taskInfo;

	@Column(name="task_name", nullable=false, length=55)
	private String taskName;

	public PlanTask() {
	}

	public int getPlanId() {
		return this.planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public Date getBegDate() {
		return this.begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Date getOverDate() {
		return this.overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public String getTaskInfo() {
		return this.taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}