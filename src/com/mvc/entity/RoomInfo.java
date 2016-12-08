package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 房间信息
 * 
 * @author zjn
 * @date 2016年12月8日
 */
@Entity
@Table(name = "room_info")
public class RoomInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer rmid;// 主键

	@ManyToOne
	@JoinColumn(name = "sort_id")
	private RoomSort roomSort;// 房间类型ID，同room_sort中的sort_id对应

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
	private Integer isdeleted;// 删除标志位，1删除、0未删除

	@Column(name = "room_description", length = 64)
	private String roomDescription;// 房间描述

	@Column(name = "guest_num", nullable = false)
	private Integer guestNum;// 客人数

	@Column(length = 100)
	private String reason;// 维修房、停用房的具体原因

	@Column(nullable = false, length = 128)
	private String remark;// 备注

	@Column(nullable = false)
	private Integer alarm;

	@Column(nullable = false)
	private Integer flag;

	@Column(name = "room_booked")
	private Integer roomBooked;

	@Column(name = "room_clean")
	private Integer roomClean;

	@Column(name = "room_online")
	private Integer roomOnline;

	@Column(name = "room_repair")
	private Integer roomRepair;

	@Column(name = "tmp_status", length = 8)
	private String tmpStatus;

	public RoomInfo() {
	}

	public Integer getRmid() {
		return this.rmid;
	}

	public void setRmid(Integer rmid) {
		this.rmid = rmid;
	}

	public Integer getAlarm() {
		return this.alarm;
	}

	public void setAlarm(Integer alarm) {
		this.alarm = alarm;
	}

	public String getCleanStatus() {
		return this.cleanStatus;
	}

	public void setCleanStatus(String cleanStatus) {
		this.cleanStatus = cleanStatus;
	}

	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getGuestNum() {
		return this.guestNum;
	}

	public void setGuestNum(Integer guestNum) {
		this.guestNum = guestNum;
	}

	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
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

	public Integer getRoomBooked() {
		return this.roomBooked;
	}

	public void setRoomBooked(Integer roomBooked) {
		this.roomBooked = roomBooked;
	}

	public Integer getRoomClean() {
		return this.roomClean;
	}

	public void setRoomClean(Integer roomClean) {
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

	public Integer getRoomOnline() {
		return this.roomOnline;
	}

	public void setRoomOnline(Integer roomOnline) {
		this.roomOnline = roomOnline;
	}

	public Integer getRoomRepair() {
		return this.roomRepair;
	}

	public void setRoomRepair(Integer roomRepair) {
		this.roomRepair = roomRepair;
	}

	public String getRoomStatus() {
		return this.roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public RoomSort getRoomSort() {
		return this.roomSort;
	}

	public void setRoomSort(RoomSort roomSort) {
		this.roomSort = roomSort;
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