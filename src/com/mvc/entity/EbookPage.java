package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ebook_page database table.
 * 
 */
@Entity
@Table(name="ebook_page")
@NamedQuery(name="EbookPage.findAll", query="SELECT e FROM EbookPage e")
public class EbookPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int pageId;

	@Column(nullable=false)
	private int bookId;

	@Column(nullable=false, length=2)
	private String lan;

	@Column(nullable=false)
	private int pageSort;

	@Column(nullable=false, length=100)
	private String pageTitle;

	@Column(nullable=false)
	private int pageType;

	@Column(nullable=false, length=200)
	private String remark;

	@Column(length=60)
	private String url;

	public EbookPage() {
	}

	public int getPageId() {
		return this.pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getLan() {
		return this.lan;
	}

	public void setLan(String lan) {
		this.lan = lan;
	}

	public int getPageSort() {
		return this.pageSort;
	}

	public void setPageSort(int pageSort) {
		this.pageSort = pageSort;
	}

	public String getPageTitle() {
		return this.pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public int getPageType() {
		return this.pageType;
	}

	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}