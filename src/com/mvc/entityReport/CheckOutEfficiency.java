/**
 * 
 */
package com.mvc.entityReport;

/**
 * @author 包阿儒汉
 *
 */
public class CheckOutEfficiency {
	private String orderNum;// 序号
	private String authorName;// 员工姓名
	private String authorNo;//员工编号
	private String sumTime;// 总用时（分钟）
	private String givenTime;// 平均给定时间（分钟）
	private String usedTimeAvg;// 平均抢房用时（分钟）
	private String workCount;// 抢房总数
	private float workEffeciencyAvg;// 平均抢房效率
	private String timeOutRate;// 超时率

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getSumTime() {
		return sumTime;
	}

	public void setSumTime(String sumTime) {
		this.sumTime = sumTime;
	}

	public String getGivenTime() {
		return givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	public String getUsedTimeAvg() {
		return usedTimeAvg;
	}

	public void setUsedTimeAvg(String usedTimeAvg) {
		this.usedTimeAvg = usedTimeAvg;
	}

	public String getWorkCount() {
		return workCount;
	}

	public void setWorkCount(String workCount) {
		this.workCount = workCount;
	}

	public float getWorkEffeciencyAvg() {
		return workEffeciencyAvg;
	}

	public void setWorkEffeciencyAvg(float workEffeciencyAvg) {
		this.workEffeciencyAvg = workEffeciencyAvg;
	}

	public String getTimeOutRate() {
		return timeOutRate;
	}

	public void setTimeOutRate(String timeOutRate) {
		this.timeOutRate = timeOutRate;
	}

	public String getAuthorNo() {
		return authorNo;
	}

	public void setAuthorNo(String authorNo) {
		this.authorNo = authorNo;
	}

}
