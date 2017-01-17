package com.mvc.entityReport;

/**
 * 客房部对客服务类型统计
 * 
 * @author wanghuimin
 * @date 2017年1月10日
 */
public class HouseCustomerServiceType {
	private String orderNum;// 序号
	private String serviceType;// 服务类型
	private Float serviceLoad;// 服务数量
	private String giveTime;// 给定时间
	private String averageWorkTime;// 平均用时
	private String timeOutServiceLoad;// 超时服务数量
	private Float timeOutRate;// 超时率
	private String serviceLoad_rank;// 总量排名
	private String timeOutRate_rank;// 超时率排

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(String giveTime) {
		this.giveTime = giveTime;
	}

	public String getAverageWorkTime() {
		return averageWorkTime;
	}

	public void setAverageWorkTime(String averageWorkTime) {
		this.averageWorkTime = averageWorkTime;
	}

	public String getTimeOutServiceLoad() {
		return timeOutServiceLoad;
	}

	public void setTimeOutServiceLoad(String timeOutServiceLoad) {
		this.timeOutServiceLoad = timeOutServiceLoad;
	}

	public Float getServiceLoad() {
		return serviceLoad;
	}

	public void setServiceLoad(Float serviceLoad) {
		this.serviceLoad = serviceLoad;
	}

	public Float getTimeOutRate() {
		return timeOutRate;
	}

	public void setTimeOutRate(Float timeOutRate) {
		this.timeOutRate = timeOutRate;
	}

	public String getServiceLoad_rank() {
		return serviceLoad_rank;
	}

	public void setServiceLoad_rank(String serviceLoad_rank) {
		this.serviceLoad_rank = serviceLoad_rank;
	}

	public String getTimeOutRate_rank() {
		return timeOutRate_rank;
	}

	public void setTimeOutRate_rank(String timeOutRate_rank) {
		this.timeOutRate_rank = timeOutRate_rank;
	}

}
