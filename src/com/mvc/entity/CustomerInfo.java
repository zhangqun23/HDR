package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the customer_info database table.
 * 
 */
@Entity
@Table(name="customer_info")
@NamedQuery(name="CustomerInfo.findAll", query="SELECT c FROM CustomerInfo c")
public class CustomerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id", unique=true, nullable=false, length=16)
	private String customerId;

	@Column(name="cert_type", length=32)
	private String certType;

	@Column(name="comp_add", length=128)
	private String compAdd;

	@Column(name="comp_name", length=128)
	private String compName;

	@Column(name="customer_accnt", length=30)
	private String customerAccnt;

	@Column(name="customer_bir", length=32)
	private String customerBir;

	@Column(name="customer_cert", length=32)
	private String customerCert;

	@Column(name="customer_like", length=64)
	private String customerLike;

	@Column(name="customer_name", length=128)
	private String customerName;

	@Column(name="customer_phone", length=32)
	private String customerPhone;

	@Column(name="customer_pic", length=128)
	private String customerPic;

	@Column(name="customer_sex", length=8)
	private String customerSex;

	@Column(length=50)
	private String enname;

	@Column(name="grp_name", length=128)
	private String grpName;

	@Column(name="grp_no", length=20)
	private String grpNo;

	private int isdeleted;

	@Column(length=20)
	private String nation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="record_time")
	private Date recordTime;

	@Column(name="recorder_id", length=16)
	private String recorderId;

	@Lob
	private String remark;

	@Column(name="room_prefer", length=32)
	private String roomPrefer;

	@Column(name="vip_class", length=32)
	private String vipClass;

	public CustomerInfo() {
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCompAdd() {
		return this.compAdd;
	}

	public void setCompAdd(String compAdd) {
		this.compAdd = compAdd;
	}

	public String getCompName() {
		return this.compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCustomerAccnt() {
		return this.customerAccnt;
	}

	public void setCustomerAccnt(String customerAccnt) {
		this.customerAccnt = customerAccnt;
	}

	public String getCustomerBir() {
		return this.customerBir;
	}

	public void setCustomerBir(String customerBir) {
		this.customerBir = customerBir;
	}

	public String getCustomerCert() {
		return this.customerCert;
	}

	public void setCustomerCert(String customerCert) {
		this.customerCert = customerCert;
	}

	public String getCustomerLike() {
		return this.customerLike;
	}

	public void setCustomerLike(String customerLike) {
		this.customerLike = customerLike;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerPic() {
		return this.customerPic;
	}

	public void setCustomerPic(String customerPic) {
		this.customerPic = customerPic;
	}

	public String getCustomerSex() {
		return this.customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	public String getEnname() {
		return this.enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getGrpNo() {
		return this.grpNo;
	}

	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoomPrefer() {
		return this.roomPrefer;
	}

	public void setRoomPrefer(String roomPrefer) {
		this.roomPrefer = roomPrefer;
	}

	public String getVipClass() {
		return this.vipClass;
	}

	public void setVipClass(String vipClass) {
		this.vipClass = vipClass;
	}

}