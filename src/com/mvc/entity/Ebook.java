package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ebook database table.
 * 
 */
@Entity
@Table(name="ebook")
@NamedQuery(name="Ebook.findAll", query="SELECT e FROM Ebook e")
public class Ebook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int bookId;

	@Column(nullable=false)
	private int adminFlag;

	@Column(nullable=false, length=45)
	private String bookTitle;

	@Column(nullable=false, length=45)
	private String creatTime;

	@Column(nullable=false)
	private int display;

	@Column(nullable=false)
	private int lan;

	@Column(nullable=false)
	private int photoHeight;

	@Column(nullable=false)
	private int photoWidth;

	@Column(length=500)
	private String remark;

	@Column(nullable=false)
	private int typeId;

	public Ebook() {
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAdminFlag() {
		return this.adminFlag;
	}

	public void setAdminFlag(int adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getBookTitle() {
		return this.bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getCreatTime() {
		return this.creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getLan() {
		return this.lan;
	}

	public void setLan(int lan) {
		this.lan = lan;
	}

	public int getPhotoHeight() {
		return this.photoHeight;
	}

	public void setPhotoHeight(int photoHeight) {
		this.photoHeight = photoHeight;
	}

	public int getPhotoWidth() {
		return this.photoWidth;
	}

	public void setPhotoWidth(int photoWidth) {
		this.photoWidth = photoWidth;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}