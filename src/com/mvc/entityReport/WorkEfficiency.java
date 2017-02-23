package com.mvc.entityReport;

/**
 * 员工工作效率统计实体
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public class WorkEfficiency {

	private String orderNum;// 序号
	private String staff_name;// 员工姓名
	private String staff_no;// 员工编号
	private String work_time;// 当班时间(min)
	private String house_time;// 做房时间(min)
	private Float house_eff;// 做房效率
	private String house_serv_time;// 做房+客服时间(min)
	private Float house_serv_eff;// 做房+客服效率

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

	public String getWork_time() {
		return work_time;
	}

	public void setWork_time(String work_time) {
		this.work_time = work_time;
	}

	public String getHouse_time() {
		return house_time;
	}

	public void setHouse_time(String house_time) {
		this.house_time = house_time;
	}

	public Float getHouse_eff() {
		return house_eff;
	}

	public void setHouse_eff(Float house_eff) {
		this.house_eff = house_eff;
	}

	public String getHouse_serv_time() {
		return house_serv_time;
	}

	public void setHouse_serv_time(String house_serv_time) {
		this.house_serv_time = house_serv_time;
	}

	public Float getHouse_serv_eff() {
		return house_serv_eff;
	}

	public void setHouse_serv_eff(Float house_serv_eff) {
		this.house_serv_eff = house_serv_eff;
	}

}
