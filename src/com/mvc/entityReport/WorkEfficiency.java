package com.mvc.entityReport;

import java.io.Serializable;

/**
 * 员工工作效率统计实体
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public class WorkEfficiency implements Serializable {

	private static final long serialVersionUID = -2213430695473645509L;

	private Integer orderNum;// 序号
	private String staff_name;// 员工姓名
	private String staff_no;// 员工编号
	/***** 抹尘房 *****/
	private String work_time_dust;// 总用时
	private String duty_time_dust;// 平均用时
	private String effi_dust;// 排名
	/***** 过夜房 *****/
	private String work_time_night;// 总用时
	private String duty_time_night;// 平均用时
	private String effi_night;// 排名
	/***** 离退房 *****/
	private String work_time_leave;// 总用时
	private String duty_time_leave;// 平均用时
	private String effi_leave;// 排名

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
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

	public String getWork_time_dust() {
		return work_time_dust;
	}

	public void setWork_time_dust(String work_time_dust) {
		this.work_time_dust = work_time_dust;
	}

	public String getDuty_time_dust() {
		return duty_time_dust;
	}

	public void setDuty_time_dust(String duty_time_dust) {
		this.duty_time_dust = duty_time_dust;
	}

	public String getEffi_dust() {
		return effi_dust;
	}

	public void setEffi_dust(String effi_dust) {
		this.effi_dust = effi_dust;
	}

	public String getWork_time_night() {
		return work_time_night;
	}

	public void setWork_time_night(String work_time_night) {
		this.work_time_night = work_time_night;
	}

	public String getDuty_time_night() {
		return duty_time_night;
	}

	public void setDuty_time_night(String duty_time_night) {
		this.duty_time_night = duty_time_night;
	}

	public String getEffi_night() {
		return effi_night;
	}

	public void setEffi_night(String effi_night) {
		this.effi_night = effi_night;
	}

	public String getWork_time_leave() {
		return work_time_leave;
	}

	public void setWork_time_leave(String work_time_leave) {
		this.work_time_leave = work_time_leave;
	}

	public String getDuty_time_leave() {
		return duty_time_leave;
	}

	public void setDuty_time_leave(String duty_time_leave) {
		this.duty_time_leave = duty_time_leave;
	}

	public String getEffi_leave() {
		return effi_leave;
	}

	public void setEffi_leave(String effi_leave) {
		this.effi_leave = effi_leave;
	}

}
