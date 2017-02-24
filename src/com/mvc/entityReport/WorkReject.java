package com.mvc.entityReport;

/**
 * 部门员工做房驳回统计实体
 * 
 * @author zq
 * @date 2016-12-08
 */
public class WorkReject {

	private String orderNum;// 序号
	private String staff_name;// 员工姓名
	private String staff_no;// 员工编号
	/***** 抹尘房 *****/
	private String num_dust;// 数量
	private String reject_dust;// 驳回数
	private Float reject_dust_eff;// 驳回率
	/***** 过夜房 *****/
	private String num_night;// 数量
	private String reject_night;// 驳回数
	private Float reject_night_eff;// 驳回率
	/***** 离退房 *****/
	private String num_leave;// 数量
	private String reject_leave;// 总用时
	private Float reject_leave_eff;// 排名

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getNum_dust() {
		return num_dust;
	}

	public void setNum_dust(String num_dust) {
		this.num_dust = num_dust;
	}

	public String getReject_dust() {
		return reject_dust;
	}

	public void setReject_dust(String reject_dust) {
		this.reject_dust = reject_dust;
	}

	public Float getReject_dust_eff() {
		return reject_dust_eff;
	}

	public void setReject_dust_eff(Float reject_dust_eff) {
		this.reject_dust_eff = reject_dust_eff;
	}

	public String getNum_night() {
		return num_night;
	}

	public void setNum_night(String num_night) {
		this.num_night = num_night;
	}

	public String getReject_night() {
		return reject_night;
	}

	public void setReject_night(String reject_night) {
		this.reject_night = reject_night;
	}

	public Float getReject_night_eff() {
		return reject_night_eff;
	}

	public void setReject_night_eff(Float reject_night_eff) {
		this.reject_night_eff = reject_night_eff;
	}

	public String getNum_leave() {
		return num_leave;
	}

	public void setNum_leave(String num_leave) {
		this.num_leave = num_leave;
	}

	public String getReject_leave() {
		return reject_leave;
	}

	public void setReject_leave(String reject_leave) {
		this.reject_leave = reject_leave;
	}

	public Float getReject_leave_eff() {
		return reject_leave_eff;
	}

	public void setReject_leave_eff(Float reject_leave_eff) {
		this.reject_leave_eff = reject_leave_eff;
	}

}
