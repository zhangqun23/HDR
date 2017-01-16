package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mini_order_detail database table.
 * 
 */
@Entity
@Table(name="mini_order_detail")
@NamedQuery(name="MiniOrderDetail.findAll", query="SELECT m FROM MiniOrderDetail m")
public class MiniOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private int goodsId;

	@Column(name="mini_order_id", nullable=false)
	private int miniOrderId;

	@Column(nullable=false)
	private int num;

	@Column(nullable=false)
	private int recordFlag;

	public MiniOrderDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getMiniOrderId() {
		return this.miniOrderId;
	}

	public void setMiniOrderId(int miniOrderId) {
		this.miniOrderId = miniOrderId;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getRecordFlag() {
		return this.recordFlag;
	}

	public void setRecordFlag(int recordFlag) {
		this.recordFlag = recordFlag;
	}

}