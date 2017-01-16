package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the charge_info database table.
 * 
 */
@Entity
@Table(name="charge_info")
@NamedQuery(name="ChargeInfo.findAll", query="SELECT c FROM ChargeInfo c")
public class ChargeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=11)
	private String code;

	@Column(length=255)
	private String name;

	@Column(name="PC_CODE", nullable=false, length=9)
	private String pcCode;

	private float price;

	@Column(length=255)
	private String typeid;

	public ChargeInfo() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPcCode() {
		return this.pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

}