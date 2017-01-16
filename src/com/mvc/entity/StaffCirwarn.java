package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_cirwarn database table.
 * 
 */
@Entity
@Table(name="staff_cirwarn")
@NamedQuery(name="StaffCirwarn.findAll", query="SELECT s FROM StaffCirwarn s")
public class StaffCirwarn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="book_id")
	private int bookId;

	@Column(length=255)
	private String is_delete;

	@Column(length=255)
	private String warn_Content;

	@Column(length=255)
	private String warn_Excute;

	@Column(length=255)
	private String warn_Id;

	@Column(length=255)
	private String warn_Interval;

	@Column(length=255)
	private String warn_State;

	@Column(length=255)
	private String warn_Time;

	@Column(length=255)
	private String warn_Way;

	public StaffCirwarn() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getIs_delete() {
		return this.is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	public String getWarn_Content() {
		return this.warn_Content;
	}

	public void setWarn_Content(String warn_Content) {
		this.warn_Content = warn_Content;
	}

	public String getWarn_Excute() {
		return this.warn_Excute;
	}

	public void setWarn_Excute(String warn_Excute) {
		this.warn_Excute = warn_Excute;
	}

	public String getWarn_Id() {
		return this.warn_Id;
	}

	public void setWarn_Id(String warn_Id) {
		this.warn_Id = warn_Id;
	}

	public String getWarn_Interval() {
		return this.warn_Interval;
	}

	public void setWarn_Interval(String warn_Interval) {
		this.warn_Interval = warn_Interval;
	}

	public String getWarn_State() {
		return this.warn_State;
	}

	public void setWarn_State(String warn_State) {
		this.warn_State = warn_State;
	}

	public String getWarn_Time() {
		return this.warn_Time;
	}

	public void setWarn_Time(String warn_Time) {
		this.warn_Time = warn_Time;
	}

	public String getWarn_Way() {
		return this.warn_Way;
	}

	public void setWarn_Way(String warn_Way) {
		this.warn_Way = warn_Way;
	}

}