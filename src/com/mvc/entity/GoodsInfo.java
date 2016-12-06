package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goods_info database table.
 * 
 */
@Entity
@Table(name="goods_info")
@NamedQuery(name="GoodsInfo.findAll", query="SELECT g FROM GoodsInfo g")
public class GoodsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int goods_id;

	@Column(length=45)
	private String code;

	@Lob
	private String description;

	@Column(nullable=false)
	private int display;

	@Column(nullable=false, length=100)
	private String goods_Ename;

	@Column(nullable=false, length=100)
	private String goods_Name;

	@Column(length=100)
	private String goods_Picture;

	@Column(nullable=false, length=30)
	private String goods_Price;

	@Column(nullable=false, length=30)
	private String goods_Typeid;

	@Column(nullable=false)
	private int goodsOrder;

	@Column(nullable=false)
	private int gWith;

	private int maxNum;

	private int newOrder;

	@Column(nullable=false, length=45)
	private String PC_Code;

	@Column(length=145)
	private String spec_cn;

	@Column(length=145)
	private String spec_en;

	@Column(nullable=false)
	private int totalOrder;

	@Column(length=20)
	private String unit_cn;

	@Column(length=30)
	private String unit_en;

	private int useNum;

	public GoodsInfo() {
	}

	public int getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public String getGoods_Ename() {
		return this.goods_Ename;
	}

	public void setGoods_Ename(String goods_Ename) {
		this.goods_Ename = goods_Ename;
	}

	public String getGoods_Name() {
		return this.goods_Name;
	}

	public void setGoods_Name(String goods_Name) {
		this.goods_Name = goods_Name;
	}

	public String getGoods_Picture() {
		return this.goods_Picture;
	}

	public void setGoods_Picture(String goods_Picture) {
		this.goods_Picture = goods_Picture;
	}

	public String getGoods_Price() {
		return this.goods_Price;
	}

	public void setGoods_Price(String goods_Price) {
		this.goods_Price = goods_Price;
	}

	public String getGoods_Typeid() {
		return this.goods_Typeid;
	}

	public void setGoods_Typeid(String goods_Typeid) {
		this.goods_Typeid = goods_Typeid;
	}

	public int getGoodsOrder() {
		return this.goodsOrder;
	}

	public void setGoodsOrder(int goodsOrder) {
		this.goodsOrder = goodsOrder;
	}

	public int getGWith() {
		return this.gWith;
	}

	public void setGWith(int gWith) {
		this.gWith = gWith;
	}

	public int getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getNewOrder() {
		return this.newOrder;
	}

	public void setNewOrder(int newOrder) {
		this.newOrder = newOrder;
	}

	public String getPC_Code() {
		return this.PC_Code;
	}

	public void setPC_Code(String PC_Code) {
		this.PC_Code = PC_Code;
	}

	public String getSpec_cn() {
		return this.spec_cn;
	}

	public void setSpec_cn(String spec_cn) {
		this.spec_cn = spec_cn;
	}

	public String getSpec_en() {
		return this.spec_en;
	}

	public void setSpec_en(String spec_en) {
		this.spec_en = spec_en;
	}

	public int getTotalOrder() {
		return this.totalOrder;
	}

	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}

	public String getUnit_cn() {
		return this.unit_cn;
	}

	public void setUnit_cn(String unit_cn) {
		this.unit_cn = unit_cn;
	}

	public String getUnit_en() {
		return this.unit_en;
	}

	public void setUnit_en(String unit_en) {
		this.unit_en = unit_en;
	}

	public int getUseNum() {
		return this.useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

}