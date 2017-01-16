package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the message_info database table.
 * 
 */
@Entity
@Table(name="message_info")
@NamedQuery(name="MessageInfo.findAll", query="SELECT m FROM MessageInfo m")
public class MessageInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int message_id;

	@Column(nullable=false, length=24)
	private String customer_id;

	@Column(length=32)
	private String customerAccnt;

	@Lob
	private String message_content;

	@Column(length=500)
	private String message_etitle;

	@Column(length=500)
	private String message_memo;

	@Column(nullable=false)
	private int message_state;

	@Column(nullable=false, length=30)
	private String message_time;

	@Column(nullable=false, length=500)
	private String message_title;

	@Column(nullable=false)
	private int message_type;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(nullable=false, length=24)
	private String room_id;

	public MessageInfo() {
	}

	public int getMessage_id() {
		return this.message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getCustomer_id() {
		return this.customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomerAccnt() {
		return this.customerAccnt;
	}

	public void setCustomerAccnt(String customerAccnt) {
		this.customerAccnt = customerAccnt;
	}

	public String getMessage_content() {
		return this.message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getMessage_etitle() {
		return this.message_etitle;
	}

	public void setMessage_etitle(String message_etitle) {
		this.message_etitle = message_etitle;
	}

	public String getMessage_memo() {
		return this.message_memo;
	}

	public void setMessage_memo(String message_memo) {
		this.message_memo = message_memo;
	}

	public int getMessage_state() {
		return this.message_state;
	}

	public void setMessage_state(int message_state) {
		this.message_state = message_state;
	}

	public String getMessage_time() {
		return this.message_time;
	}

	public void setMessage_time(String message_time) {
		this.message_time = message_time;
	}

	public String getMessage_title() {
		return this.message_title;
	}

	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}

	public int getMessage_type() {
		return this.message_type;
	}

	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}

	public String getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getRoom_id() {
		return this.room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

}