package com.mvc.entityReport;

/**
 * 工程物料实体
 * 
 * @author wangrui
 * @date 2017年2月20日
 */
public class EngineMaterial {

	private String orderNum;// 序号
	private String material_sort;// 材料类型
	private String material_name;// 材料名称
	private String material_type;// 材料型号
	private String take_amount;// 用量
	private String mterial_unit;// 材料单位
	private String task_num;// 任务数
	private String avg_task_num;// 平均用量

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getMaterial_sort() {
		return material_sort;
	}

	public void setMaterial_sort(String material_sort) {
		this.material_sort = material_sort;
	}

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public String getMaterial_type() {
		return material_type;
	}

	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}

	public String getTake_amount() {
		return take_amount;
	}

	public void setTake_amount(String take_amount) {
		this.take_amount = take_amount;
	}

	public String getMterial_unit() {
		return mterial_unit;
	}

	public void setMterial_unit(String mterial_unit) {
		this.mterial_unit = mterial_unit;
	}

	public String getTask_num() {
		return task_num;
	}

	public void setTask_num(String task_num) {
		this.task_num = task_num;
	}

	public String getAvg_task_num() {
		return avg_task_num;
	}

	public void setAvg_task_num(String avg_task_num) {
		this.avg_task_num = avg_task_num;
	}

}
