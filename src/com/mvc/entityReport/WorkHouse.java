package com.mvc.entityReport;

import java.io.Serializable;

/**
 * 部门员工做房统计实体
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public class WorkHouse implements Serializable {

	private static final long serialVersionUID = -1122213927463011548L;

	private Integer orderNum;// 序号
	private String staff_name;// 员工姓名
	private String staff_no;// 员工编号
	/***** 抹尘房 *****/
	private String num_dust;// 数量
	private String total_time_dust;// 总用时
	private String avg_time_dust;// 平均用时
	private String rank_dust;// 排名
	/***** 过夜房 *****/
	private String num_night;// 数量
	private String total_time_night;// 总用时
	private String avg_time_night;// 平均用时
	private String rank_night;// 排名
	/***** 离退房 *****/
	private String num_leave;// 数量
	private String total_time_leave;// 总用时
	private String avg_time_leave;// 平均用时
	private String rank_leave;// 排名

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

	public String getNum_dust() {
		return num_dust;
	}

	public void setNum_dust(String num_dust) {
		this.num_dust = num_dust;
	}

	public String getTotal_time_dust() {
		return total_time_dust;
	}

	public void setTotal_time_dust(String total_time_dust) {
		this.total_time_dust = total_time_dust;
	}

	public String getAvg_time_dust() {
		return avg_time_dust;
	}

	public void setAvg_time_dust(String avg_time_dust) {
		this.avg_time_dust = avg_time_dust;
	}

	public String getRank_dust() {
		return rank_dust;
	}

	public void setRank_dust(String rank_dust) {
		this.rank_dust = rank_dust;
	}

	public String getNum_night() {
		return num_night;
	}

	public void setNum_night(String num_night) {
		this.num_night = num_night;
	}

	public String getTotal_time_night() {
		return total_time_night;
	}

	public void setTotal_time_night(String total_time_night) {
		this.total_time_night = total_time_night;
	}

	public String getAvg_time_night() {
		return avg_time_night;
	}

	public void setAvg_time_night(String avg_time_night) {
		this.avg_time_night = avg_time_night;
	}

	public String getRank_night() {
		return rank_night;
	}

	public void setRank_night(String rank_night) {
		this.rank_night = rank_night;
	}

	public String getNum_leave() {
		return num_leave;
	}

	public void setNum_leave(String num_leave) {
		this.num_leave = num_leave;
	}

	public String getTotal_time_leave() {
		return total_time_leave;
	}

	public void setTotal_time_leave(String total_time_leave) {
		this.total_time_leave = total_time_leave;
	}

	public String getAvg_time_leave() {
		return avg_time_leave;
	}

	public void setAvg_time_leave(String avg_time_leave) {
		this.avg_time_leave = avg_time_leave;
	}

	public String getRank_leave() {
		return rank_leave;
	}

	public void setRank_leave(String rank_leave) {
		this.rank_leave = rank_leave;
	}

}
