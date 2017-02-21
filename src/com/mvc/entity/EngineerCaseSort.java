package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Entity
@Table(name = "engineer_case_sort")
public class EngineerCaseSort implements Serializable {
	private static final long serialVersionUID = 1L;

	private int sortId;// 主键
	private int delFlag;
	private String departId;//部门id
	private int fatherId;//父id
	private int givenTime;
	private byte isCustomerService;
	private byte needCheck;
	private String parentName;//父类型名称
	private int showOrder;
	private String sortName;//子类型名称

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sort_id", unique = true, nullable = false)
	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	@Column(name = "del_flag")
	public int getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "depart_id")
	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	@Column(name = "father_id")
	public int getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}

	@Column(name = "given_time")
	public int getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(int givenTime) {
		this.givenTime = givenTime;
	}

	@Column(name = "is_customer_service")
	public byte getIsCustomerService() {
		return this.isCustomerService;
	}

	public void setIsCustomerService(byte isCustomerService) {
		this.isCustomerService = isCustomerService;
	}

	@Column(name = "need_check")
	public byte getNeedCheck() {
		return this.needCheck;
	}

	public void setNeedCheck(byte needCheck) {
		this.needCheck = needCheck;
	}

	@Column(name = "parent_name", length = 25)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "show_order")
	public int getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@Column(name = "sort_name", length = 25)
	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}