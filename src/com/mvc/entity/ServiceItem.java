package com.mvc.entity;

import javax.persistence.*;

/**
 * 服务类型 
 * 
 */
@Entity
@Table(name = "service_items")
public class ServiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer serviceId;

	@Column(name = "service_name", nullable = false, length = 64)
	private String serviceName;// 服务类型

	@Column(name = "depart_id", length = 32)
	private String departId;// 所属部门，同department_info中depart_id对应

	@Column(nullable = false)
	private Integer display;// 显示标志，1显示、0不显示

	@Column(nullable = false)
	private Integer fatherId;// 父类ID

	@Column(name = "given_time", length = 25)
	private String givenTime;// 给定完成时间，默认单位为分钟

	@Column(name = "is_customer_service", nullable = false)
	private byte isCustomerService;// 对客标志，1对客、0对内、2既对客又对内

	@Column(nullable = false)
	private Integer isCharge;// 该服务是否收费，0不收费、1收费、2收费+服务费

	@Column(name = "need_check", nullable = false)
	private byte needCheck;// 是否需要验收，1是0否

	@Column(name = "parent_name", length = 64)
	private String parentName;// 父类名称

	@Column(name = "show_order")
	private Integer showOrder;// 排序

	@Column(length = 64)
	private String typeId;// 服务对应的分类，同goods_type中的Goods_TpeId对应

	public ServiceItem() {
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(Integer fatherId) {
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

	public Integer getIsCharge() {
		return this.isCharge;
	}

	public void setIsCharge(Integer isCharge) {
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

	public Integer getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}