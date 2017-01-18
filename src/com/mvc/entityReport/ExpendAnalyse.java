package com.mvc.entityReport;

/**
 * 消耗分析
 * 
 * @author wq
 * @date 2017年1月12日
 */
public class ExpendAnalyse {
	
	private String orderNum;// 序号
	private String goods_name;// 物品名称
	private String goods_num;// 物品数量
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

}
