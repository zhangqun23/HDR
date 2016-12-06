package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room_info database table.
 * 
 */
@Entity
@Table(name="room_info")
@NamedQuery(name="RoomInfo.findAll", query="SELECT r FROM RoomInfo r")
public class RoomInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int rmid;

	@Column(nullable=false)
	private int alarm;

	@Column(name="clean_status", length=12)
	private String cleanStatus;

	@Column(nullable=false)
	private int flag;

	@Column(name="guest_num", nullable=false)
	private int guestNum;

	@Column(nullable=false)
	private int isdeleted;

	@Column(length=100)
	private String reason;

	@Column(nullable=false, length=128)
	private String remark;

	@Column(name="room_booked")
	private int roomBooked;

	@Column(name="room_clean")
	private int roomClean;

	@Column(name="room_description", length=64)
	private String roomDescription;

	@Column(name="room_floor", length=8)
	private String roomFloor;

	@Column(name="room_floor_info", length=10)
	private String roomFloorInfo;

	@Column(name="room_id", nullable=false, length=16)
	private String roomId;

	@Column(name="room_no", length=16)
	private String roomNo;

	@Column(name="room_online")
	private int roomOnline;

	@Column(name="room_repair")
	private int roomRepair;

	@Column(name="room_status", length=12)
	private String roomStatus;

	@Column(name="sort_id", nullable=false)
	private int sortId;

	@Column(name="sort_no", nullable=false, length=10)
	private String sortNo;

	@Column(name="tmp_status", length=8)
	private String tmpStatus;

	public RoomInfo() {
	}

	public int getRmid() {
		return this.rmid;
	}

	public void setRmid(int rmid) {
		this.rmid = rmid;
	}

	public int getAlarm() {
		return this.alarm;
	}

	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}

	public String getCleanStatus() {
		return this.cleanStatus;
	}

	public void setCleanStatus(String cleanStatus) {
		this.cleanStatus = cleanStatus;
	}

	public int getFlag() {
		return this.flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getGuestNum() {
		return this.guestNum;
	}

	public void setGuestNum(int guestNum) {
		this.guestNum = guestNum;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRoomBooked() {
		return this.roomBooked;
	}

	public void setRoomBooked(int roomBooked) {
		this.roomBooked = roomBooked;
	}

	public int getRoomClean() {
		return this.roomClean;
	}

	public void setRoomClean(int roomClean) {
		this.roomClean = roomClean;
	}

	public String getRoomDescription() {
		return this.roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getRoomFloor() {
		return this.roomFloor;
	}

	public void setRoomFloor(String roomFloor) {
		this.roomFloor = roomFloor;
	}

	public String getRoomFloorInfo() {
		return this.roomFloorInfo;
	}

	public void setRoomFloorInfo(String roomFloorInfo) {
		this.roomFloorInfo = roomFloorInfo;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getRoomOnline() {
		return this.roomOnline;
	}

	public void setRoomOnline(int roomOnline) {
		this.roomOnline = roomOnline;
	}

	public int getRoomRepair() {
		return this.roomRepair;
	}

	public void setRoomRepair(int roomRepair) {
		this.roomRepair = roomRepair;
	}

	public String getRoomStatus() {
		return this.roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getTmpStatus() {
		return this.tmpStatus;
	}

	public void setTmpStatus(String tmpStatus) {
		this.tmpStatus = tmpStatus;
	}

}