package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the public_area database table.
 * 
 */
@Entity
@Table(name="public_area")
@NamedQuery(name="PublicArea.findAll", query="SELECT p FROM PublicArea p")
public class PublicArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="area_id", unique=true, nullable=false)
	private int areaId;

	@Column(name="area_name", nullable=false, length=16)
	private String areaName;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="repair_dep_id", length=10)
	private String repairDepId;

	@Column(name="show_order")
	private byte showOrder;

	public PublicArea() {
	}

	public int getAreaId() {
		return this.areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRepairDepId() {
		return this.repairDepId;
	}

	public void setRepairDepId(String repairDepId) {
		this.repairDepId = repairDepId;
	}

	public byte getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(byte showOrder) {
		this.showOrder = showOrder;
	}

}