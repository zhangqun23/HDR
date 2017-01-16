package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the movie_charge database table.
 * 
 */
@Entity
@Table(name="movie_charge")
@NamedQuery(name="MovieCharge.findAll", query="SELECT m FROM MovieCharge m")
public class MovieCharge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="charge_id", unique=true, nullable=false)
	private int chargeId;

	@Column(name="charge_flag", nullable=false)
	private int chargeFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="charge_time", nullable=false)
	private Date chargeTime;

	@Column(name="room_id", nullable=false, length=16)
	private String roomId;

	public MovieCharge() {
	}

	public int getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(int chargeId) {
		this.chargeId = chargeId;
	}

	public int getChargeFlag() {
		return this.chargeFlag;
	}

	public void setChargeFlag(int chargeFlag) {
		this.chargeFlag = chargeFlag;
	}

	public Date getChargeTime() {
		return this.chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}