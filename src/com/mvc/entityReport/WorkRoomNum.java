package com.mvc.entityReport;

/**
 * 客房部员工打扫房间数汇总表
 * 
 * @author zjn
 * @date 2017年2月17日
 */
public class WorkRoomNum {

	private String orderNum;// 序号
	private String staffName;// 员工姓名
	private String staffNo;// 员工编号

	private Integer cleanRoom;// 抹尘房数量0
	private Integer checkoutRoom;// 离退房数量1
	private Integer overnightRoom;// 过夜房数量2

	private Integer totalNum;// 总数量
	private String rank;// 排名

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

	public Integer getCleanRoom() {
		return cleanRoom;
	}

	public void setCleanRoom(Integer cleanRoom) {
		this.cleanRoom = cleanRoom;
	}

	public Integer getCheckoutRoom() {
		return checkoutRoom;
	}

	public void setCheckoutRoom(Integer checkoutRoom) {
		this.checkoutRoom = checkoutRoom;
	}

	public Integer getOvernightRoom() {
		return overnightRoom;
	}

	public void setOvernightRoom(Integer overnightRoom) {
		this.overnightRoom = overnightRoom;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}
