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

	private Integer rmid;// 主键
	private RoomSort roomSort;// 房间类型ID，同room_sort中的sort_id对应
	private String roomFloor;// 所在楼层
	private String roomFloorInfo;// 房间所属区域，同hotel_area中的area_no对应
	private String roomId;// 房间号
	private String roomNo;// 房间号
	private String cleanStatus;// 房间清洁状态，0脏房、1干净房、2微脏房、3已查干净房
	private String roomStatus;// 房间状态，V空房、O住人、U维修、S锁房、A临时房态（根据不同的酒馆系统而变）
	private String sortNo;// 房间类型编码，同room_sort中的sort_no对应
	private Integer isdeleted;// 删除标志位，1删除、0未删除
	private String roomDescription;// 房间描述
	private Integer guestNum;// 客人数
	private String reason;// 维修房、停用房的具体原因
	private String remark;// 备注
	private Integer alarm;
	private Integer flag;
	private Integer roomBooked;
	private Integer roomClean;
	private Integer roomOnline;
	private Integer roomRepair;
	private String tmpStatus;

	public RoomInfo() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	public Integer getRmid() {
		return this.rmid;
	}

	public void setRmid(Integer rmid) {
		this.rmid = rmid;
	}

	@Column(nullable = false)
	public Integer getAlarm() {
		return this.alarm;
	}

	public void setAlarm(Integer alarm) {
		this.alarm = alarm;
	}

	@Column(name = "clean_status", length = 12)
	public String getCleanStatus() {
		return this.cleanStatus;
	}

	public void setCleanStatus(String cleanStatus) {
		this.cleanStatus = cleanStatus;
	}

	@Column(nullable = false)
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "guest_num", nullable = false)
	public Integer getGuestNum() {
		return this.guestNum;
	}

	public void setGuestNum(Integer guestNum) {
		this.guestNum = guestNum;
	}

	@Column(nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@Column(length = 100)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(nullable = false, length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "room_booked")
	public Integer getRoomBooked() {
		return this.roomBooked;
	}

	public void setRoomBooked(Integer roomBooked) {
		this.roomBooked = roomBooked;
	}

	@Column(name = "room_clean")
	public Integer getRoomClean() {
		return this.roomClean;
	}

	public void setRoomClean(Integer roomClean) {
		this.roomClean = roomClean;
	}

	@Column(name = "room_description", length = 64)
	public String getRoomDescription() {
		return this.roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	@Column(name = "room_floor", length = 8)
	public String getRoomFloor() {
		return this.roomFloor;
	}

	public void setRoomFloor(String roomFloor) {
		this.roomFloor = roomFloor;
	}

	@Column(name = "room_floor_info", length = 10)
	public String getRoomFloorInfo() {
		return this.roomFloorInfo;
	}

	public void setRoomFloorInfo(String roomFloorInfo) {
		this.roomFloorInfo = roomFloorInfo;
	}

	@Column(name = "room_id", nullable = false, length = 16)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "room_no", length = 16)
	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "room_online")
	public Integer getRoomOnline() {
		return this.roomOnline;
	}

	public void setRoomOnline(Integer roomOnline) {
		this.roomOnline = roomOnline;
	}

	@Column(name = "room_repair")
	public Integer getRoomRepair() {
		return this.roomRepair;
	}

	public void setRoomRepair(Integer roomRepair) {
		this.roomRepair = roomRepair;
	}

	@Column(name = "room_status", length = 12)
	public String getRoomStatus() {
		return this.roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	@ManyToOne
	@JoinColumn(name = "sort_id")
	public RoomSort getRoomSort() {
		return this.roomSort;
	}

	public void setRoomSort(RoomSort roomSort) {
		this.roomSort = roomSort;
	}

	@Column(name = "sort_no", nullable = false, length = 10)
	public String getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "tmp_status", length = 8)
	public String getTmpStatus() {
		return this.tmpStatus;
	}

	public void setTmpStatus(String tmpStatus) {
		this.tmpStatus = tmpStatus;
	}

}