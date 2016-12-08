package com.mvc.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 物品
 * 
 * @author wanghuimin
 * @date 2016年12月8日
 */
@Entity
@Table(name = "goods_info")
<<<<<<< HEAD
public class GoodsInfo {
	private Integer goodsId;// 物品ID，主键
	private String goodsName;// 物品中文名称
	private String goodsEname;// 物品英文名称
=======
public class GoodsInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer goods_id;// 物品ID，主键
	private String goods_Name;// 物品中文名称
	private String goods_Ename;// 物品英文名称
>>>>>>> 2dd52c9cec39a9289df09cbd19e89086247e3324
	private String code;// code值，一般根据酒馆而定
	private String description;// 描述
	private Integer display;// 显示标志，1显示、0不显示
	private String goodsPicture;// 物品图片
	private String goodsPrice;// 物品价钱
	private String goodsTypeid;// 物品类型
	private Integer goodsOrder;// 显示排序
	private Integer gWith;// 图片宽度
	private Integer maxNum;// 最大可用数量，-1代表不限使用数量
	private Integer newOrder;// 耗品领用排序
	private String pcCode;
	private String specCn;
	private String specEn;
	private Integer totalOrder;
	private String unitCn;
	private String unitEn;
	private Integer useNum;

	public GoodsInfo() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="goods_id")
	public Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	@Column(length = 45)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Lob
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false)
	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	@Column(name="goods_ename",nullable = false, length = 100)
	public String getGoodsEname() {
		return this.goodsEname;
	}

	public void setGoodsEname(String goodsEname) {
		this.goodsEname = goodsEname;
	}

	@Column(name="goods_name",nullable = false, length = 100)
	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name="goods_picture",length = 100)
	public String getGoodsPicture() {
		return this.goodsPicture;
	}

	public void setGoodsPicture(String goodsPicture) {
		this.goodsPicture = goodsPicture;
	}

	@Column(name="goods_price",nullable = false, length = 30)
	public String getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	@Column(name="goods_typeid",nullable = false, length = 30)
	public String getGoodsTypeid() {
		return this.goodsTypeid;
	}

	public void setGoodsTypeid(String goodsTypeid) {
		this.goodsTypeid = goodsTypeid;
	}

	@Column(name="goodsorder",nullable = false)
	public Integer getGoodsOrder() {
		return this.goodsOrder;
	}

	public void setGoodsOrder(Integer goodsOrder) {
		this.goodsOrder = goodsOrder;
	}

	@Column(name="gwith",nullable = false)
	public Integer getGWith() {
		return this.gWith;
	}

	public void setGWith(Integer gWith) {
		this.gWith = gWith;
	}

	@Column(name="maxnum")
	public Integer getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	@Column(name="neworder")
	public Integer getNewOrder() {
		return this.newOrder;
	}

	public void setNewOrder(Integer newOrder) {
		this.newOrder = newOrder;
	}

	@Column(name="pc_code",nullable = false, length = 45)
	public String getpcCode() {
		return this.pcCode;
	}

	public void setpcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	@Column(name="spec_cn",length = 145)
	public String getSpecCn() {
		return this.specCn;
	}

	public void setSpecCn(String specCn) {
		this.specCn = specCn;
	}

	@Column(name="spec_en",length = 145)
	public String getSpecEn() {
		return this.specEn;
	}

	public void setSpecEn(String specEn) {
		this.specEn = specEn;
	}

	@Column(name="totalorder",nullable = false)
	public Integer getTotalOrder() {
		return this.totalOrder;
	}

	public void setTotalOrder(Integer totalOrder) {
		this.totalOrder = totalOrder;
	}

	@Column(name="unit_cn",length = 20)
	public String getUnitCn() {
		return this.unitCn;
	}

	public void setUnitCn(String unitCn) {
		this.unitCn = unitCn;
	}

	@Column(name="unit_en",length = 30)
	public String getUnitEn() {
		return this.unitEn;
	}

	public void setUnitEn(String unitEn) {
		this.unitEn = unitEn;
	}
	@Column(name="usenum")
	public Integer getUseNum() {
		return this.useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

}