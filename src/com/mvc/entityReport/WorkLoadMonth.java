package com.mvc.entityReport;

/**
 * 按月统计员工工作量
 * 
 * @author zjn
 * @date 2017年1月12日
 */
public class WorkLoadMonth {
	private Integer month;// 月份
	private Float actualLoad;// 客房部员工单个月份实际总工作量
	private Float ratedLoad;// 客房部员工单个月份额定总工作量
	private Integer proActWorkLoad;// 工程部员工单个月份实际总工作量

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Float getActualLoad() {
		return actualLoad;
	}

	public void setActualLoad(Float actualLoad) {
		this.actualLoad = actualLoad;
	}

	public Float getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(Float ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

	public Integer getProActWorkLoad() {
		return proActWorkLoad;
	}

	public void setProActWorkLoad(Integer proActWorkLoad) {
		this.proActWorkLoad = proActWorkLoad;
	}

}
