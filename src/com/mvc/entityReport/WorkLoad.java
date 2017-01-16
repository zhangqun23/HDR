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
<<<<<<< HEAD
	private String cleanRoom;// 抹尘房0
	private String checkoutRoom;// 离退房1
	private String overnightRoom;// 过夜房2

//	private String ratedLoad;// 额定工作量
	private String actualLoad;// 实际工作量
	private String beyondLoad;// 超出工作量
=======

	private Float cleanRoom;// 抹尘房0
	private Float checkoutRoom;// 离退房1
	private Float overnightRoom;// 过夜房2

	private Float actualLoad;// 实际工作量
	private Float beyondLoad;// 超出工作量
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
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

<<<<<<< HEAD
//	public String getRatedLoad() {
//		return ratedLoad;
//	}
//
//	public void setRatedLoad(String ratedLoad) {
//		this.ratedLoad = ratedLoad;
//	}
=======
	public Float getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(Float ratedLoad) {
		this.ratedLoad = ratedLoad;
	}
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e

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
