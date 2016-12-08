package com.mvc.entity;

import javax.persistence.*;

/**
 * 服务类型
 * 
 * @author zjn
 * @date 2016年12月8日
 */
@Entity
@Table(name = "service_items")
public class ServiceItem {

	private Integer serviceId;
	private String serviceName;// 服务类型
	private String departId;// 所属部门，同department_info中depart_id对应
	private Integer display;// 显示标志，1显示、0不显示
	private Integer fatherId;// 父类ID
	private String givenTime;// 给定完成时间，默认单位为分钟
	private byte isCustomerService;// 对客标志，1对客、0对内、2既对客又对内
	private Integer isCharge;// 该服务是否收费，0不收费、1收费、2收费+服务费
	private byte needCheck;// 是否需要验收，1是0否
	private String parentName;// 父类名称
	private Integer showOrder;// 排序
	private String typeId;// 服务对应的分类，同goods_type中的Goods_TpeId对应

	public ServiceItem() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "depart_id", length = 32)
	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	@Column(nullable = false)
	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	@Column(nullable = false)
	public Integer getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	@Column(name = "given_time", length = 25)
	public String getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	@Column(name = "is_customer_service", nullable = false)
	public byte getIsCustomerService() {
		return this.isCustomerService;
	}

	public void setIsCustomerService(byte isCustomerService) {
		this.isCustomerService = isCustomerService;
	}

	@Column(nullable = false)
	public Integer getIsCharge() {
		return this.isCharge;
	}

	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}

	@Column(name = "need_check", nullable = false)
	public byte getNeedCheck() {
		return this.needCheck;
	}

	public void setNeedCheck(byte needCheck) {
		this.needCheck = needCheck;
	}

	@Column(name = "parent_name", length = 64)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "service_name", nullable = false, length = 64)
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "show_order")
	public Integer getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	@Column(length = 64)
	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}