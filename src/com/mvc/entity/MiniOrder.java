package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mini_order database table.
 * 
 */
@Entity
@Table(name="mini_order")
@NamedQuery(name="MiniOrder.findAll", query="SELECT m FROM MiniOrder m")
public class MiniOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mini_order_id", unique=true, nullable=false)
	private int miniOrderId;

	@Column(length=20)
	private String accnt;

	@Column(name="mini_order_no", nullable=false, length=20)
	private String miniOrderNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time", nullable=false)
	private Date recordTime;

	@Column(name="room_no", nullable=false, length=8)
	private String roomNo;

	@Column(name="staff_id", nullable=false)
	private int staffId;

	public MiniOrder() {
	}

	public int getMiniOrderId() {
		return this.miniOrderId;
	}

	public void setMiniOrderId(int miniOrderId) {
		this.miniOrderId = miniOrderId;
	}

	public String getAccnt() {
		return this.accnt;
	}

	public void setAccnt(String accnt) {
		this.accnt = accnt;
	}

	public String getMiniOrderNo() {
		return this.miniOrderNo;
	}

	public void setMiniOrderNo(String miniOrderNo) {
		this.miniOrderNo = miniOrderNo;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

}