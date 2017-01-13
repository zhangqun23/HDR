package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the equip_info database table.
 * 
 */
@Entity
@Table(name="equip_info")
@NamedQuery(name="EquipInfo.findAll", query="SELECT e FROM EquipInfo e")
public class EquipInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="equip_id", unique=true, nullable=false)
	private int equipId;

	@Column(name="equip_author", length=5)
	private String equipAuthor;

	@Column(name="equip_department", length=20)
	private String equipDepartment;

	@Temporal(TemporalType.DATE)
	@Column(name="equip_first_time", nullable=false)
	private Date equipFirstTime;

	@Column(name="equip_flag", length=255)
	private String equipFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="equip_last_time", nullable=false)
	private Date equipLastTime;

	@Column(name="equip_name", nullable=false, length=16)
	private String equipName;

	@Column(name="equip_recycle_time", nullable=false, length=32)
	private String equipRecycleTime;

	@Column(name="equip_room", length=16)
	private String equipRoom;

	@Column(name="equip_sort", length=32)
	private String equipSort;

	@Column(name="equip_value", length=255)
	private String equipValue;

	@Column(name="fixed_asset_no", length=20)
	private String fixedAssetNo;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="is_warranty_warn", nullable=false)
	private byte isWarrantyWarn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_notify_time")
	private Date lastNotifyTime;

	@Temporal(TemporalType.DATE)
	@Column(name="out_warranty")
	private Date outWarranty;

	@Column(name="warranty_tel", length=18)
	private String warrantyTel;

	@Column(name="warranty_unit", length=255)
	private String warrantyUnit;

	public EquipInfo() {
	}

	public int getEquipId() {
		return this.equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public String getEquipAuthor() {
		return this.equipAuthor;
	}

	public void setEquipAuthor(String equipAuthor) {
		this.equipAuthor = equipAuthor;
	}

	public String getEquipDepartment() {
		return this.equipDepartment;
	}

	public void setEquipDepartment(String equipDepartment) {
		this.equipDepartment = equipDepartment;
	}

	public Date getEquipFirstTime() {
		return this.equipFirstTime;
	}

	public void setEquipFirstTime(Date equipFirstTime) {
		this.equipFirstTime = equipFirstTime;
	}

	public String getEquipFlag() {
		return this.equipFlag;
	}

	public void setEquipFlag(String equipFlag) {
		this.equipFlag = equipFlag;
	}

	public Date getEquipLastTime() {
		return this.equipLastTime;
	}

	public void setEquipLastTime(Date equipLastTime) {
		this.equipLastTime = equipLastTime;
	}

	public String getEquipName() {
		return this.equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getEquipRecycleTime() {
		return this.equipRecycleTime;
	}

	public void setEquipRecycleTime(String equipRecycleTime) {
		this.equipRecycleTime = equipRecycleTime;
	}

	public String getEquipRoom() {
		return this.equipRoom;
	}

	public void setEquipRoom(String equipRoom) {
		this.equipRoom = equipRoom;
	}

	public String getEquipSort() {
		return this.equipSort;
	}

	public void setEquipSort(String equipSort) {
		this.equipSort = equipSort;
	}

	public String getEquipValue() {
		return this.equipValue;
	}

	public void setEquipValue(String equipValue) {
		this.equipValue = equipValue;
	}

	public String getFixedAssetNo() {
		return this.fixedAssetNo;
	}

	public void setFixedAssetNo(String fixedAssetNo) {
		this.fixedAssetNo = fixedAssetNo;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public byte getIsWarrantyWarn() {
		return this.isWarrantyWarn;
	}

	public void setIsWarrantyWarn(byte isWarrantyWarn) {
		this.isWarrantyWarn = isWarrantyWarn;
	}

	public Date getLastNotifyTime() {
		return this.lastNotifyTime;
	}

	public void setLastNotifyTime(Date lastNotifyTime) {
		this.lastNotifyTime = lastNotifyTime;
	}

	public Date getOutWarranty() {
		return this.outWarranty;
	}

	public void setOutWarranty(Date outWarranty) {
		this.outWarranty = outWarranty;
	}

	public String getWarrantyTel() {
		return this.warrantyTel;
	}

	public void setWarrantyTel(String warrantyTel) {
		this.warrantyTel = warrantyTel;
	}

	public String getWarrantyUnit() {
		return this.warrantyUnit;
	}

	public void setWarrantyUnit(String warrantyUnit) {
		this.warrantyUnit = warrantyUnit;
	}

}