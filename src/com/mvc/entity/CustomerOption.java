package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the customer_option database table.
 * 
 */
@Entity
@Table(name="customer_option")
@NamedQuery(name="CustomerOption.findAll", query="SELECT c FROM CustomerOption c")
public class CustomerOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int optionId;

	@Column(name="book_id", nullable=false)
	private int bookId;

	@Column(nullable=false)
	private int keySum;

	@Column(nullable=false, length=45)
	private String optionDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date optionTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date startRecodTiem;

	public CustomerOption() {
	}

	public int getOptionId() {
		return this.optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getKeySum() {
		return this.keySum;
	}

	public void setKeySum(int keySum) {
		this.keySum = keySum;
	}

	public String getOptionDate() {
		return this.optionDate;
	}

	public void setOptionDate(String optionDate) {
		this.optionDate = optionDate;
	}

	public Date getOptionTime() {
		return this.optionTime;
	}

	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}

	public Date getStartRecodTiem() {
		return this.startRecodTiem;
	}

	public void setStartRecodTiem(Date startRecodTiem) {
		this.startRecodTiem = startRecodTiem;
	}

}