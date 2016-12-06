package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the service_items database table.
 * 
 */
@Entity
@Table(name="service_items")
@NamedQuery(name="ServiceItem.findAll", query="SELECT s FROM ServiceItem s")
public class ServiceItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int serviceId;

	@Column(name="depart_id", length=32)
	private String departId;

	@Column(nullable=false)
	private int display;

	@Column(nullable=false)
	private int fatherId;

	@Column(name="given_time", length=25)
	private String givenTime;

	@Column(name="is_customer_service", nullable=false)
	private byte isCustomerService;

	@Column(nullable=false)
	private int isCharge;

	@Column(name="need_check", nullable=false)
	private byte needCheck;

	@Column(name="parent_name", length=64)
	private String parentName;

	@Column(name="service_name", nullable=false, length=64)
	private String serviceName;

	@Column(name="show_order")
	private int showOrder;

	@Column(length=64)
	private String typeId;

	public ServiceItem() {
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}

	public String getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	public byte getIsCustomerService() {
		return this.isCustomerService;
	}

	public void setIsCustomerService(byte isCustomerService) {
		this.isCustomerService = isCustomerService;
	}

	public int getIsCharge() {
		return this.isCharge;
	}

	public void setIsCharge(int isCharge) {
		this.isCharge = isCharge;
	}

	public byte getNeedCheck() {
		return this.needCheck;
	}

	public void setNeedCheck(byte needCheck) {
		this.needCheck = needCheck;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}