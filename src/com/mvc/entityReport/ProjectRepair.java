package com.mvc.entityReport;

/**
 * 工程维修项统计分析
 * 
 * @author wanghuimin
 * @date 2017年2月20日
 */
public class ProjectRepair {
	private String orderNum;// 序号
	private String repairParentType;// 维修父类型
	private String repairType;// 维修子类型
	private Integer serviceLoad;// 服务数量
	private String engineerAmount;// 总计

	public Integer getServiceLoad() {
		return serviceLoad;
	}

	public void setServiceLoad(Integer serviceLoad) {
		this.serviceLoad = serviceLoad;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getRepairParentType() {
		return repairParentType;
	}

	public void setRepairParentType(String repairParentType) {
		this.repairParentType = repairParentType;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getEngineerAmount() {
		return engineerAmount;
	}

	public void setEngineerAmount(String engineerAmount) {
		this.engineerAmount = engineerAmount;
	}

}
