package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the routine_task_item_used database table.
 * 
 */
@Entity
@Table(name="routine_task_item_used")
@NamedQuery(name="RoutineTaskItemUsed.findAll", query="SELECT r FROM RoutineTaskItemUsed r")
public class RoutineTaskItemUsed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="rtiu_id", unique=true, nullable=false)
	private int rtiuId;

	@Column(name="case_id", nullable=false, length=20)
	private String caseId;

	@Column(name="rti_id")
	private int rtiId;

	@Column(name="rti_num", nullable=false)
	private int rtiNum;

	@Column(name="rti_title", length=255)
	private String rtiTitle;

	public RoutineTaskItemUsed() {
	}

	public int getRtiuId() {
		return this.rtiuId;
	}

	public void setRtiuId(int rtiuId) {
		this.rtiuId = rtiuId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public int getRtiId() {
		return this.rtiId;
	}

	public void setRtiId(int rtiId) {
		this.rtiId = rtiId;
	}

	public int getRtiNum() {
		return this.rtiNum;
	}

	public void setRtiNum(int rtiNum) {
		this.rtiNum = rtiNum;
	}

	public String getRtiTitle() {
		return this.rtiTitle;
	}

	public void setRtiTitle(String rtiTitle) {
		this.rtiTitle = rtiTitle;
	}

}