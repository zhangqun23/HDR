package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 房间信息 The persistent class for the room_info database table.
 * 
 */
@Entity
@Table(name = "room_info")
@NamedQuery(name = "RoomInfo.findAll", query = "SELECT r FROM RoomInfo r")
public class RoomInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int rmid;// 主键

	@ManyToOne
	@JoinColumn(name = "sort_id")
	private RoomSort sortId;// 房间类型ID，同room_sort中的sort_id对应

	@Column(name = "room_floor", length = 8)
	private String roomFloor;// 所在楼层

	@Column(name = "room_floor_info", length = 10)
	private String roomFloorInfo;// 房间所属区域，同hotel_area中的area_no对应

	@Column(name = "room_id", nullable = false, length = 16)
	private String roomId;// 房间号

	@Column(name = "room_no", length = 16)
	private String roomNo;// 房间号

	@Column(name = "clean_status", length = 12)
	private String cleanStatus;// 房间清洁状态，0脏房、1干净房、2微脏房、3已查干净房

	@Column(name = "room_status", length = 12)
	private String roomStatus;// 房间状态，V空房、O住人、U维修、S锁房、A临时房态（根据不同的酒馆系统而变）

	@Column(name = "sort_no", nullable = false, length = 10)
	private String sortNo;// 房间类型编码，同room_sort中的sort_no对应

	@Column(nullable = false)
	private int isdeleted;// 删除标志位，1删除、0未删除

	@Column(name = "room_description", length = 64)
	private String roomDescription;// 房间描述

	@Column(name = "guest_num", nullable = false)
	private int guestNum;// 客人数

	@Column(length = 100)
	private String reason;// 维修房、停用房的具体原因

	@Column(nullable = false, length = 128)
	private String remark;// 备注

	@Column(nullable = false)
	private int alarm;

	@Column(nullable = false)
	private int flag;

	@Column(name = "room_booked")
	private int roomBooked;

	@Column(name = "room_clean")
	private int roomClean;

	@Column(name = "room_online")
	private int roomOnline;

	@Column(name = "room_repair")
	private int roomRepair;

	@Column(name = "tmp_status", length = 8)
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

	public RoomSort getSortId() {
		return this.sortId;
	}

	public void setSortId(RoomSort sortId) {
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