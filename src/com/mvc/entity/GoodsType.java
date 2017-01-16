package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goods_type database table.
 * 
 */
@Entity
@Table(name="goods_type")
@NamedQuery(name="GoodsType.findAll", query="SELECT g FROM GoodsType g")
public class GoodsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=30)
	private String goods_TypeId;

	@Column(nullable=false)
	private int display;

	@Column(nullable=false)
	private int hasSub;

	@Column(nullable=false)
	private int isWith;

	@Column(nullable=false)
	private int maxNum;

	@Column(nullable=false, length=45)
	private String parentId;

	@Column(name="photo_flag", nullable=false)
	private int photoFlag;

	@Column(nullable=false, length=100)
	private String typeEname;

	@Column(nullable=false, length=45)
	private String typeName;

	@Column(nullable=false)
	private int typeOrder;

	public GoodsType() {
	}

	public String getGoods_TypeId() {
		return this.goods_TypeId;
	}

	public void setGoods_TypeId(String goods_TypeId) {
		this.goods_TypeId = goods_TypeId;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getHasSub() {
		return this.hasSub;
	}

	public void setHasSub(int hasSub) {
		this.hasSub = hasSub;
	}

	public int getIsWith() {
		return this.isWith;
	}

	public void setIsWith(int isWith) {
		this.isWith = isWith;
	}

	public int getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getPhotoFlag() {
		return this.photoFlag;
	}

	public void setPhotoFlag(int photoFlag) {
		this.photoFlag = photoFlag;
	}

	public String getTypeEname() {
		return this.typeEname;
	}

	public void setTypeEname(String typeEname) {
		this.typeEname = typeEname;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeOrder() {
		return this.typeOrder;
	}

	public void setTypeOrder(int typeOrder) {
		this.typeOrder = typeOrder;
	}

}