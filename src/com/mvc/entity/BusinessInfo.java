package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the business_info database table.
 * 
 */
@Entity
@Table(name="business_info")
@NamedQuery(name="BusinessInfo.findAll", query="SELECT b FROM BusinessInfo b")
public class BusinessInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=40)
	private String business_id;

	@Column(nullable=false, length=30)
	private String business_name;

	@Column(nullable=false, length=200)
	private String business_path;

	@Column(name="image_path", nullable=false, length=200)
	private String imagePath;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(length=30)
	private String reserve3;

	public BusinessInfo() {
	}

	public String getBusiness_id() {
		return this.business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public String getBusiness_name() {
		return this.business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getBusiness_path() {
		return this.business_path;
	}

	public void setBusiness_path(String business_path) {
		this.business_path = business_path;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return this.reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

}