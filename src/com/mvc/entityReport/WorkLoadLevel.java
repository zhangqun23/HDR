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

	private String workDays;// 实际工作天数
	private Float ratedLoad;// 额定工作量
	private Float actualLoad;// 实际工作量
	private Float beyondLoad;// 超出工作量
	private String outOfRang;// 超出幅度
	private Float outOfRang2;// 超出幅度、冗余字段

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

	public Float getOutOfRang2() {
		return outOfRang2;
	}

	public void setOutOfRang2(Float outOfRang2) {
		this.outOfRang2 = outOfRang2;
	}

}
