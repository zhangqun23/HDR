/**
 * 
 */
package com.mvc.entityReport;

/**
 * 按月统计工作量
 * 
 * @author zjn
 * @date 2017年1月12日
 */
public class WorkLoadMonth {
	private String month;// 月份
	private String actualLoad;// 该月份实际总工作量
	private String ratedLoad;// 该月份额定总工作量

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getActualLoad() {
		return actualLoad;
	}

	public void setActualLoad(String actualLoad) {
		this.actualLoad = actualLoad;
	}

	public String getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(String ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

}
