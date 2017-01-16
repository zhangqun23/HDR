package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the material_house database table.
 * 
 */
@Entity
@Table(name="material_house")
@NamedQuery(name="MaterialHouse.findAll", query="SELECT m FROM MaterialHouse m")
public class MaterialHouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="material_id", unique=true, nullable=false)
	private int materialId;

	@Column(name="fsort_id")
	private int fsortId;

	@Column(nullable=false)
	private byte is_deleted;

	@Column(name="material_name", nullable=false, length=50)
	private String materialName;

	@Column(name="material_no", length=255)
	private String materialNo;

	@Column(name="material_type", nullable=false, length=50)
	private String materialType;

	@Column(name="material_unit", length=255)
	private String materialUnit;

	@Column(name="oaupdate_flag")
	private byte oaupdateFlag;

	@Column(name="operate_flag", nullable=false)
	private byte operateFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="operate_time", nullable=false)
	private Date operateTime;

	@Column(name="sort_id")
	private int sortId;

	public MaterialHouse() {
	}

	public int getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public int getFsortId() {
		return this.fsortId;
	}

	public void setFsortId(int fsortId) {
		this.fsortId = fsortId;
	}

	public byte getIs_deleted() {
		return this.is_deleted;
	}

	public void setIs_deleted(byte is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialNo() {
		return this.materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialType() {
		return this.materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialUnit() {
		return this.materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public byte getOaupdateFlag() {
		return this.oaupdateFlag;
	}

	public void setOaupdateFlag(byte oaupdateFlag) {
		this.oaupdateFlag = oaupdateFlag;
	}

	public byte getOperateFlag() {
		return this.operateFlag;
	}

	public void setOperateFlag(byte operateFlag) {
		this.operateFlag = operateFlag;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

}