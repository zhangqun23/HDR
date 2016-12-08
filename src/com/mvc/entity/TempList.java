package com.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 房间消耗品
 * 
 * @author wanghuimin
 * @date 2016年12月8日
 */
@Entity
@Table(name = "temp_list")
public class TempList {
	private Integer id;// 主键
	private GoodsInfo goodsId;// 物品ID，同goods_info中的Goods_Id对应
	private StaffInfo staffId;// 员工ID，同staff_info中的staff_id对应
	private String areaNo;// 房间所属区域
	private String callId;// 所属任务的ID，同call_info中的call_id对应
	private Integer isdeleted;// 删除标志位，1删除、0未删除
	private Integer num;// 物品使用数量
	private Integer orderType;// 物品类型
	private String roomNo;// 任务所属房间号码
	private Date usedTime;// 使用时间

	public TempList() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "area_no", length = 255)
	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	@Column(name = "call_id", nullable = false, length = 14)
	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@Column(nullable = false)
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "order_type", nullable = false)
	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(name = "room_no", length = 10)
	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@ManyToOne
	@JoinColumn(name = "goods_id")
	public GoodsInfo getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(GoodsInfo goodsId) {
		this.goodsId = goodsId;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id")
	public StaffInfo getStaffId() {
		return staffId;
	}

	public void setStaffId(StaffInfo staffId) {
		this.staffId = staffId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "used_time", nullable = false)
	public Date getUsedTime() {
		return this.usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

}