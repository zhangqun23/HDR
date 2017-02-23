package com.mvc.entityReport;

/**
 * 工程部员工工作量汇总表
 * 
 * @author zjn
 * @date 2017年2月20日
 */
public class ProjectWorkLoad {

	private String orderNum;// 序号
	private String staffName;// 员工姓名
	private String staffNo;// 员工编号
	private Integer workLoad;// 工作量
	private String rank;// 排名

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Integer getWorkLoad() {
		return workLoad;
	}

	public void setWorkLoad(Integer workLoad) {
		this.workLoad = workLoad;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}
