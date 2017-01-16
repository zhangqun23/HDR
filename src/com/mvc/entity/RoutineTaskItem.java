package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the routine_task_item database table.
 * 
 */
@Entity
@Table(name="routine_task_item")
@NamedQuery(name="RoutineTaskItem.findAll", query="SELECT r FROM RoutineTaskItem r")
public class RoutineTaskItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="rti_id", unique=true, nullable=false)
	private int rtiId;

	@Column(name="rti_flag", nullable=false)
	private int rtiFlag;

	@Column(name="rti_pid", nullable=false)
	private int rtiPid;

	@Column(name="rti_title", nullable=false, length=255)
	private String rtiTitle;

	@Column(name="show_order", nullable=false)
	private int showOrder;

	public RoutineTaskItem() {
	}

	public int getRtiId() {
		return this.rtiId;
	}

	public void setRtiId(int rtiId) {
		this.rtiId = rtiId;
	}

	public int getRtiFlag() {
		return this.rtiFlag;
	}

	public void setRtiFlag(int rtiFlag) {
		this.rtiFlag = rtiFlag;
	}

	public int getRtiPid() {
		return this.rtiPid;
	}

	public void setRtiPid(int rtiPid) {
		this.rtiPid = rtiPid;
	}

	public String getRtiTitle() {
		return this.rtiTitle;
	}

	public void setRtiTitle(String rtiTitle) {
		this.rtiTitle = rtiTitle;
	}

	public int getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

}