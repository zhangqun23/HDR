package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 房间类型
 * 
 * @author zjn
 * @date 2016年12月8日
 */
@Entity
@Table(name = "room_sort")
public class RoomSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sort_id", unique = true, nullable = false)
	private Integer sortId;// 主键

	@Column(name = "sort_name", length = 16)
	private String sortName;// 房间类型名称

	@Column(name = "bed_num")
	private Integer bedNum;// 床数

	private Integer isdeleted;// 记录删除标志位，1删除、0未删除

	@Column(name = "room_pic", length = 128)
	private String roomPic;// 房间图片

	@Column(name = "sort_no", nullable = false, length = 10)
	private String sortNo;// 房间类型编码

	@Column(nullable = false)
	private Integer weight;// 图片宽度

	public RoomSort() {
	}

	public Integer getSortId() {
		return this.sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getBedNum() {
		return this.bedNum;
	}

	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}

	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
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

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}