package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the linen_house database table.
 * 
 */
@Entity
@Table(name="linen_house")
@NamedQuery(name="LinenHouse.findAll", query="SELECT l FROM LinenHouse l")
public class LinenHouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int linen_Id;

	@Column(nullable=false)
	private byte is_Deleted;

	@Column(length=10)
	private String linen_name;

	@Column(length=10)
	private String linen_type;

	@Column(name="linen_unit", length=20)
	private String linenUnit;

	@Column(name="sort_id", nullable=false)
	private int sortId;

	public LinenHouse() {
	}

	public int getLinen_Id() {
		return this.linen_Id;
	}

	public void setLinen_Id(int linen_Id) {
		this.linen_Id = linen_Id;
	}

	public byte getIs_Deleted() {
		return this.is_Deleted;
	}

	public void setIs_Deleted(byte is_Deleted) {
		this.is_Deleted = is_Deleted;
	}

	public String getLinen_name() {
		return this.linen_name;
	}

	public void setLinen_name(String linen_name) {
		this.linen_name = linen_name;
	}

	public String getLinen_type() {
		return this.linen_type;
	}

	public void setLinen_type(String linen_type) {
		this.linen_type = linen_type;
	}

	public String getLinenUnit() {
		return this.linenUnit;
	}

	public void setLinenUnit(String linenUnit) {
		this.linenUnit = linenUnit;
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

}