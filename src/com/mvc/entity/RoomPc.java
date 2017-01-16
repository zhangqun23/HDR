package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room_pc database table.
 * 
 */
@Entity
@Table(name="room_pc")
@NamedQuery(name="RoomPc.findAll", query="SELECT r FROM RoomPc r")
public class RoomPc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pc_id", unique=true, nullable=false)
	private int pcId;

	@Column(nullable=false, length=15)
	private String ip;

	@Column(name="pc_remark", length=255)
	private String pcRemark;

	private int rmid;

	@Column(name="room_id", nullable=false, length=16)
	private String roomId;

	public RoomPc() {
	}

	public int getPcId() {
		return this.pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPcRemark() {
		return this.pcRemark;
	}

	public void setPcRemark(String pcRemark) {
		this.pcRemark = pcRemark;
	}

	public int getRmid() {
		return this.rmid;
	}

	public void setRmid(int rmid) {
		this.rmid = rmid;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}