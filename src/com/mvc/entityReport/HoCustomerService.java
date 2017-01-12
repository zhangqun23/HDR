package com.mvc.entityReport;

/**
 * 酒店对客服务信息统计
 * 
 * @author wanghuimin
 * @date 2017年1月10日
 */
public class HoCustomerService {
	private String orderNum;// 序号
	private String department;// 部门
	private String serviceLoad;// 服务数量
	private String timeOutService;// 超时服务
	private String timeOutRate;// 超时率
	private String sumWorkTime;// 总用时
	private String averageWorkTime;// 平均用时
	private String serviceLoad_rank;// 总量排名
	private String timeOutRate_rank;// 超时率排名

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getTimeOutService() {
		return timeOutService;
	}

	public void setTimeOutService(String timeOutService) {
		this.timeOutService = timeOutService;
	}

	public String getTimeOutRate() {
		return timeOutRate;
	}

	public void setTimeOutRate(String timeOutRate) {
		this.timeOutRate = timeOutRate;
	}

	public String getTimeOutRate_rank() {
		return timeOutRate_rank;
	}

	public void setTimeOutRate_rank(String timeOutRate_rank) {
		this.timeOutRate_rank = timeOutRate_rank;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getServiceLoad() {
		return serviceLoad;
	}

	public void setServiceLoad(String serviceLoad) {
		this.serviceLoad = serviceLoad;
	}

	public String getSumWorkTime() {
		return sumWorkTime;
	}

	public void setSumWorkTime(String sumWorkTime) {
		this.sumWorkTime = sumWorkTime;
	}

	public String getAverageWorkTime() {
		return averageWorkTime;
	}

	public void setAverageWorkTime(String averageWorkTime) {
		this.averageWorkTime = averageWorkTime;
	}

	public String getServiceLoad_rank() {
		return serviceLoad_rank;
	}

	public void setServiceLoad_rank(String serviceLoad_rank) {
		this.serviceLoad_rank = serviceLoad_rank;
	}

}
