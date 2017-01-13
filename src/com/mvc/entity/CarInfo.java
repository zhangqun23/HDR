package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the car_info database table.
 * 
 */
@Entity
@Table(name="car_info")
@NamedQuery(name="CarInfo.findAll", query="SELECT c FROM CarInfo c")
public class CarInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=40)
	private String car_id;

	@Column(nullable=false, length=100)
	private String car_ImgPath;

	@Column(nullable=false, length=30)
	private String car_name;

	@Column(nullable=false, length=20)
	private String car_price;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(length=30)
	private String reserve3;

	public CarInfo() {
	}

	public String getCar_id() {
		return this.car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getCar_ImgPath() {
		return this.car_ImgPath;
	}

	public void setCar_ImgPath(String car_ImgPath) {
		this.car_ImgPath = car_ImgPath;
	}

	public String getCar_name() {
		return this.car_name;
	}

	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}

	public String getCar_price() {
		return this.car_price;
	}

	public void setCar_price(String car_price) {
		this.car_price = car_price;
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