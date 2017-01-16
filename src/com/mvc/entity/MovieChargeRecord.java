package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the movie_charge_record database table.
 * 
 */
@Entity
@Table(name="movie_charge_record")
@NamedQuery(name="MovieChargeRecord.findAll", query="SELECT m FROM MovieChargeRecord m")
public class MovieChargeRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="record_id", unique=true, nullable=false)
	private int recordId;

	@Column(name="customer_id", nullable=false, length=16)
	private String customerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time", nullable=false)
	private Date recordTime;

	@Column(name="room_id", nullable=false, length=16)
	private String roomId;

	public MovieChargeRecord() {
	}

	public int getRecordId() {
		return this.recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}