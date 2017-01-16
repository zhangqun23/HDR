package com.mvc.entityReport;

public class RobDetail {
	private String orderNum;// 序号
	private String roomNo;// 房号
	private String usedTime;// 做房时间（分钟）
	private String givenTime;// 给定时间（分钟）
	private String workEffeciency;// 效率
	private String authorName;// 完成员工
	private String isBack;// 驳回次数
	private String checkUsedTime;// 检查用时（分钟）
	private String CheckerName;// 检查人

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}

	public String getGivenTime() {
		return givenTime;
	}

	public void setGivenTime(String givenTime) {
		this.givenTime = givenTime;
	}

	public String getWorkEffeciency() {
		return workEffeciency;
	}

	public void setWorkEffeciency(String workEffeciency) {
		this.workEffeciency = workEffeciency;
	}

	public String getAuthor_name() {
		return authorName;
	}

	public void setAuthor_name(String author_name) {
		this.authorName = author_name;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getCheckUsedTime() {
		return checkUsedTime;
	}

	public void setCheckUsedTime(String checkUsedTime) {
		this.checkUsedTime = checkUsedTime;
	}

	public String getCheckerName() {
		return CheckerName;
	}

	public void setCheckerName(String checkerName) {
		CheckerName = checkerName;
	}

}
