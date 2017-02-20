package com.mvc.entityReport;

/**
 * 客房部员工按月统计工作量
 * 
 * @author zjn
 * @date 2017年1月12日
 */
public class WorkLoadMonth {
	private Integer month;// 月份
	private Float actualLoad;// 该月份实际总工作量
	private Float ratedLoad;// 该月份额定总工作量

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

}
