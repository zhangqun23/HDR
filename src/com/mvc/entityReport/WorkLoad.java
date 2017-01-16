package com.mvc.entityReport;

/**
 * 员工工作量汇总表
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public class WorkLoad {

	private String orderNum;// 序号
	private String staffName;// 员工姓名
	private String staffNo;// 员工编号

	private Float cleanRoom;// 抹尘房0
	private Float checkoutRoom;// 离退房1
	private Float overnightRoom;// 过夜房2

	private Float actualLoad;// 实际工作量
	private Float beyondLoad;// 超出工作量
	private String rank;// 排名

	private Float ratedLoad;// 额定工作量
	private String workDays;// 实际工作天数

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Float getCleanRoom() {
		return cleanRoom;
	}

	public void setCleanRoom(Float cleanRoom) {
		this.cleanRoom = cleanRoom;
	}

	public Float getCheckoutRoom() {
		return checkoutRoom;
	}

	public void setCheckoutRoom(Float checkoutRoom) {
		this.checkoutRoom = checkoutRoom;
	}

	public Float getOvernightRoom() {
		return overnightRoom;
	}

	public void setOvernightRoom(Float overnightRoom) {
		this.overnightRoom = overnightRoom;
	}

	public Float getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(Float ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

	public Float getActualLoad() {
		return actualLoad;
	}

	public void setActualLoad(Float actualLoad) {
		this.actualLoad = actualLoad;
	}

	public Float getBeyondLoad() {
		return beyondLoad;
	}

	public void setBeyondLoad(Float beyondLoad) {
		this.beyondLoad = beyondLoad;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

}
