package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the engineer_case_sort database table.
 * 
 */
@Entity
@Table(name="engineer_case_sort")
@NamedQuery(name="EngineerCaseSort.findAll", query="SELECT e FROM EngineerCaseSort e")
public class EngineerCaseSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sort_id", unique=true, nullable=false)
	private int sortId;

	@Column(name="del_flag", nullable=false)
	private int delFlag;

	@Column(name="depart_id", length=10)
	private String departId;

	@Column(name="father_id", nullable=false)
	private int fatherId;

	@Column(name="given_time")
	private int givenTime;

	@Column(name="is_customer_service", nullable=false)
	private byte isCustomerService;

	@Column(name="need_check", nullable=false)
	private byte needCheck;

	@Column(name="parent_name", nullable=false, length=25)
	private String parentName;

	@Column(name="show_order")
	private int showOrder;

	@Column(name="sort_name", nullable=false, length=25)
	private String sortName;

	public EngineerCaseSort() {
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public int getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}

	public int getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(int givenTime) {
		this.givenTime = givenTime;
	}

	public byte getIsCustomerService() {
		return this.isCustomerService;
	}

	public void setIsCustomerService(byte isCustomerService) {
		this.isCustomerService = isCustomerService;
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

	public int getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}