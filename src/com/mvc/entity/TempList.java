package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * 
 * @author wanghuimin
 * @date 2016年12月8日
 */
@Entity
@Table(name="temp_list")
@NamedQuery(name="TempList.findAll", query="SELECT t FROM TempList t")
public class TempList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;//主键
	
	@OneToMany
	@JoinColumn(name="goods_id")
	@Column(name="goods_id", nullable=false, length=45)
	private String goodsId;//物品ID，同goods_info中的Goods_Id对应
	
	@OneToMany
	@JoinColumn(name = "staff_id")
	@Column(name="staff_id", nullable=false)
	private int staffId;//员工ID，同staff_info中的staff_id对应

	@Column(name="area_no", length=255)
	private String areaNo;//房间所属区域

	@Column(name="call_id", nullable=false, length=14)
	private String callId;//所属任务的ID，同call_info中的call_id对应

	private int isdeleted;//删除标志位，1删除、0未删除

	@Column(nullable=false)
	private int num;//物品使用数量

	@Column(name="order_type", nullable=false)
	private int orderType;//物品类型

	@Column(name="room_no", length=10)
	private String roomNo;//任务所属房间号码	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="used_time", nullable=false)
	private Date usedTime;//使用时间

	public TempList() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getOrderType() {
		return this.orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public Date getUsedTime() {
		return this.usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

}