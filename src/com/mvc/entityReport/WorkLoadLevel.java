package com.mvc.entityReport;

/**
 * 员工工作量饱和度分析
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public class WorkLoadLevel {

	private String orderNum;// 序号
	private String staffName;// 员工姓名
	private String staffNo;// 员工编号
<<<<<<< HEAD
	// private String ratedLoad;// 额定工作量
	private String actualLoad;// 实际工作量
	private String beyondLoad;// 超出工作量
=======

	private String workDays;// 实际工作天数
	private Float actualLoad;// 实际工作量
	private Float beyondLoad;// 超出工作量
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
	private String outOfRang;// 超出幅度

	private Float ratedLoad;// 额定工作量

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

<<<<<<< HEAD
	// public String getRatedLoad() {
	// return ratedLoad;
	// }
	//
	// public void setRatedLoad(String ratedLoad) {
	// this.ratedLoad = ratedLoad;
	// }

	public String getActualLoad() {
=======
	public Float getActualLoad() {
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
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

	public String getOutOfRang() {
		return outOfRang;
	}

	public void setOutOfRang(String outOfRang) {
		this.outOfRang = outOfRang;
	}

	public Float getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(Float ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

}
