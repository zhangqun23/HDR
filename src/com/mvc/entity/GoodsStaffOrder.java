package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the goods_staff_order database table.
 * 
 */
@Entity
@Table(name="goods_staff_order")
@NamedQuery(name="GoodsStaffOrder.findAll", query="SELECT g FROM GoodsStaffOrder g")
public class GoodsStaffOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="gso_id", unique=true, nullable=false)
	private int gsoId;

	@Column(name="area_no", length=16)
	private String areaNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date gso_createTime;

	@Column(name="gso_recorder", nullable=false, length=30)
	private String gsoRecorder;

	@Column(name="gso_staff_id", nullable=false)
	private int gsoStaffId;

	@Column(name="gso_states", nullable=false)
	private int gsoStates;

	public GoodsStaffOrder() {
	}

	public int getGsoId() {
		return this.gsoId;
	}

	public void setGsoId(int gsoId) {
		this.gsoId = gsoId;
	}

	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public Date getGso_createTime() {
		return this.gso_createTime;
	}

	public void setGso_createTime(Date gso_createTime) {
		this.gso_createTime = gso_createTime;
	}

	public String getGsoRecorder() {
		return this.gsoRecorder;
	}

	public void setGsoRecorder(String gsoRecorder) {
		this.gsoRecorder = gsoRecorder;
	}

	public int getGsoStaffId() {
		return this.gsoStaffId;
	}

	public void setGsoStaffId(int gsoStaffId) {
		this.gsoStaffId = gsoStaffId;
	}

	public int getGsoStates() {
		return this.gsoStates;
	}

	public void setGsoStates(int gsoStates) {
		this.gsoStates = gsoStates;
	}

}