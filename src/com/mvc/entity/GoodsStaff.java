package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the goods_staff database table.
 * 
 */
@Entity
@Table(name="goods_staff")
@NamedQuery(name="GoodsStaff.findAll", query="SELECT g FROM GoodsStaff g")
public class GoodsStaff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="goods_staff_id", unique=true, nullable=false)
	private int goodsStaffId;

	@Column(name="area_no", nullable=false, length=16)
	private String areaNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="goods_id", nullable=false)
	private int goodsId;

	@Column(name="goods_num", nullable=false)
	private int goodsNum;

	@Column(name="goods_num_ext", nullable=false)
	private int goodsNumExt;

	@Column(name="gso_id", nullable=false)
	private int gsoId;

	@Column(nullable=false, length=30)
	private String recorder;

	@Column(name="staff_id", nullable=false)
	private int staffId;

	public GoodsStaff() {
	}

	public int getGoodsStaffId() {
		return this.goodsStaffId;
	}

	public void setGoodsStaffId(int goodsStaffId) {
		this.goodsStaffId = goodsStaffId;
	}

	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public int getGoodsNumExt() {
		return this.goodsNumExt;
	}

	public void setGoodsNumExt(int goodsNumExt) {
		this.goodsNumExt = goodsNumExt;
	}

	public int getGsoId() {
		return this.gsoId;
	}

	public void setGsoId(int gsoId) {
		this.gsoId = gsoId;
	}

	public String getRecorder() {
		return this.recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

}