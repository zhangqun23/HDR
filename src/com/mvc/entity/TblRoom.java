package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_room database table.
 * 
 */
@Entity
@Table(name="tbl_room")
@NamedQuery(name="TblRoom.findAll", query="SELECT t FROM TblRoom t")
public class TblRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="room_id", unique=true, nullable=false)
	private int roomId;

	@Temporal(TemporalType.DATE)
	@Column(name="room_book", nullable=false)
	private Date roomBook;

	@Column(name="room_int")
	private int roomInt;

	@Column(name="room_name", nullable=false, length=8)
	private String roomName;

	@Lob
	@Column(name="room_remark")
	private String roomRemark;

	@Column(name="room_status", nullable=false, length=8)
	private String roomStatus;

	public TblRoom() {
	}

	public int getRoomId() {
		return this.roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Date getRoomBook() {
		return this.roomBook;
	}

	public void setRoomBook(Date roomBook) {
		this.roomBook = roomBook;
	}

	public int getRoomInt() {
		return this.roomInt;
	}

	public void setRoomInt(int roomInt) {
		this.roomInt = roomInt;
	}

	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomRemark() {
		return this.roomRemark;
	}

	public void setRoomRemark(String roomRemark) {
		this.roomRemark = roomRemark;
	}

	public String getRoomStatus() {
		return this.roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

}