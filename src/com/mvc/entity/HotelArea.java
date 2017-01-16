package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotel_area database table.
 * 
 */
@Entity
@Table(name="hotel_area")
@NamedQuery(name="HotelArea.findAll", query="SELECT h FROM HotelArea h")
public class HotelArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="hotel_area_id", unique=true, nullable=false)
	private int hotelAreaId;

	@Column(name="area_info", nullable=false, length=50)
	private String areaInfo;

	@Column(name="area_no", nullable=false, length=20)
	private String areaNo;

	@Column(name="area_type", nullable=false)
	private int areaType;

	@Column(name="father_no", nullable=false, length=10)
	private String fatherNo;

	@Column(name="hotel_area_name", nullable=false, length=50)
	private String hotelAreaName;

	@Column(nullable=false)
	private int isdel;

	@Column(name="view_order")
	private int viewOrder;

	public HotelArea() {
	}

	public int getHotelAreaId() {
		return this.hotelAreaId;
	}

	public void setHotelAreaId(int hotelAreaId) {
		this.hotelAreaId = hotelAreaId;
	}

	public String getAreaInfo() {
		return this.areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public int getAreaType() {
		return this.areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public String getFatherNo() {
		return this.fatherNo;
	}

	public void setFatherNo(String fatherNo) {
		this.fatherNo = fatherNo;
	}

	public String getHotelAreaName() {
		return this.hotelAreaName;
	}

	public void setHotelAreaName(String hotelAreaName) {
		this.hotelAreaName = hotelAreaName;
	}

	public int getIsdel() {
		return this.isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public int getViewOrder() {
		return this.viewOrder;
	}

	public void setViewOrder(int viewOrder) {
		this.viewOrder = viewOrder;
	}

}