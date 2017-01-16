package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the customer_room_rela database table.
 * 
 */
@Entity
@Table(name="customer_room_rela")
@NamedQuery(name="CustomerRoomRela.findAll", query="SELECT c FROM CustomerRoomRela c")
public class CustomerRoomRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_id", unique=true, nullable=false)
	private int bookId;

	@Column(name="accnt_flag", length=10)
	private String accntFlag;

	@Column(name="accnt_type", nullable=false)
	private int accntType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="arr_time")
	private Date arrTime;

	@Column(name="book_flag")
	private int bookFlag;

	@Column(name="confirm_flag", nullable=false)
	private int confirmFlag;

	@Column(name="crr_create_time", nullable=false)
	private Timestamp crrCreateTime;

	@Column(name="customer_accnt", length=32)
	private String customerAccnt;

	@Column(name="customer_from", nullable=false)
	private int customerFrom;

	@Column(name="customer_id", length=16)
	private String customerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dep_time")
	private Date depTime;

	@Column(name="guest_from", length=50)
	private String guestFrom;

	private int isdeleted;

	@Column(name="master_flg", nullable=false)
	private int masterFlg;

	@Column(name="mobile_pwd", length=10)
	private String mobilePwd;

	@Column(name="movie_flag", nullable=false)
	private int movieFlag;

	@Column(name="pay_type", nullable=false, length=10)
	private String payType;

	@Column(length=128)
	private String remark;

	@Column(name="room_id", length=16)
	private String roomId;

	@Column(name="sync_flag")
	private int syncFlag;

	public CustomerRoomRela() {
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getAccntFlag() {
		return this.accntFlag;
	}

	public void setAccntFlag(String accntFlag) {
		this.accntFlag = accntFlag;
	}

	public int getAccntType() {
		return this.accntType;
	}

	public void setAccntType(int accntType) {
		this.accntType = accntType;
	}

	public Date getArrTime() {
		return this.arrTime;
	}

	public void setArrTime(Date arrTime) {
		this.arrTime = arrTime;
	}

	public int getBookFlag() {
		return this.bookFlag;
	}

	public void setBookFlag(int bookFlag) {
		this.bookFlag = bookFlag;
	}

	public int getConfirmFlag() {
		return this.confirmFlag;
	}

	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public Timestamp getCrrCreateTime() {
		return this.crrCreateTime;
	}

	public void setCrrCreateTime(Timestamp crrCreateTime) {
		this.crrCreateTime = crrCreateTime;
	}

	public String getCustomerAccnt() {
		return this.customerAccnt;
	}

	public void setCustomerAccnt(String customerAccnt) {
		this.customerAccnt = customerAccnt;
	}

	public int getCustomerFrom() {
		return this.customerFrom;
	}

	public void setCustomerFrom(int customerFrom) {
		this.customerFrom = customerFrom;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getDepTime() {
		return this.depTime;
	}

	public void setDepTime(Date depTime) {
		this.depTime = depTime;
	}

	public String getGuestFrom() {
		return this.guestFrom;
	}

	public void setGuestFrom(String guestFrom) {
		this.guestFrom = guestFrom;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public int getMasterFlg() {
		return this.masterFlg;
	}

	public void setMasterFlg(int masterFlg) {
		this.masterFlg = masterFlg;
	}

	public String getMobilePwd() {
		return this.mobilePwd;
	}

	public void setMobilePwd(String mobilePwd) {
		this.mobilePwd = mobilePwd;
	}

	public int getMovieFlag() {
		return this.movieFlag;
	}

	public void setMovieFlag(int movieFlag) {
		this.movieFlag = movieFlag;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getSyncFlag() {
		return this.syncFlag;
	}

	public void setSyncFlag(int syncFlag) {
		this.syncFlag = syncFlag;
	}

}