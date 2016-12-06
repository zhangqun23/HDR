package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room_sort database table.
 * 
 */
@Entity
@Table(name="room_sort")
@NamedQuery(name="RoomSort.findAll", query="SELECT r FROM RoomSort r")
public class RoomSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sort_id", unique=true, nullable=false)
	private int sortId;

	@Column(name="bed_num")
	private int bedNum;

	private int isdeleted;

	@Column(name="room_pic", length=128)
	private String roomPic;

	@Column(name="sort_name", length=16)
	private String sortName;

	@Column(name="sort_no", nullable=false, length=10)
	private String sortNo;

	@Column(nullable=false)
	private int weight;

	public RoomSort() {
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public int getBedNum() {
		return this.bedNum;
	}

	public void setBedNum(int bedNum) {
		this.bedNum = bedNum;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getRoomPic() {
		return this.roomPic;
	}

	public void setRoomPic(String roomPic) {
		this.roomPic = roomPic;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}