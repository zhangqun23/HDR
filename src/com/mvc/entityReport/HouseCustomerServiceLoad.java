package com.mvc.entityReport;

/**
 * 客房部对客服务工作量统计
 * 
 * @author wanghuimin
 * @date 2017年1月10日
 */
public class HouseCustomerServiceLoad {
	private String orderNum;// 序号
	private String staff_name;// 员工姓名
	private String staff_no;// 员工编号
	private Float serviceLoad;// 服务数量
	private String timeOutService;// 超时服务
	private String sumWorkTime;// 总用时
	private String averageWorkTime;// 平均用时
	private Float timeOutRate;// 超时率
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

	public Float getTimeOutRate() {
		return timeOutRate;
	}

	public void setTimeOutRate(Float timeOutRate) {
		this.timeOutRate = timeOutRate;
	}

	public String getTimeOutRate_rank() {
		return timeOutRate_rank;
	}

	public void setTimeOutRate_rank(String timeOutRate_rank) {
		this.timeOutRate_rank = timeOutRate_rank;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public Float getServiceLoad() {
		return serviceLoad;
	}

	public void setServiceLoad(Float serviceLoad) {
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
