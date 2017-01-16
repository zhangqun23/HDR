package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the service_info database table.
 * 
 */
@Entity
@Table(name="service_info")
@NamedQuery(name="ServiceInfo.findAll", query="SELECT s FROM ServiceInfo s")
public class ServiceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(length=30)
	private String reserve3;

	@Column(nullable=false, length=24)
	private String room_id;

	@Id
	@Column(nullable=false, length=24)
	private String service_id;

	@Column(nullable=false, length=30)
	private String service_insertTime;

	@Column(nullable=false, length=1000)
	private String service_memo;

	@Column(nullable=false, length=50)
	private String service_typeName;

	public ServiceInfo() {
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

	public String getRoom_id() {
		return this.room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public String getService_id() {
		return this.service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getService_insertTime() {
		return this.service_insertTime;
	}

	public void setService_insertTime(String service_insertTime) {
		this.service_insertTime = service_insertTime;
	}

	public String getService_memo() {
		return this.service_memo;
	}

	public void setService_memo(String service_memo) {
		this.service_memo = service_memo;
	}

	public String getService_typeName() {
		return this.service_typeName;
	}

	public void setService_typeName(String service_typeName) {
		this.service_typeName = service_typeName;
	}

}