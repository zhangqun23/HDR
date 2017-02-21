package com.mvc.entityReport;

/**
 * @author 包阿儒汉
 *
 */
public class CheckOutDetail {
	private String orderNum;// 序号
	private String roomNo;// 房号
	private String usedTime;// 做房时间（分钟）
	private String givenTime;// 给定时间（分钟）
	private String workEffeciency;// 效率
	private String authorName;// 完成员工

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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String author_name) {
		this.authorName = author_name;
	}

}
