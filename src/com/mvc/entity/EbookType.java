package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ebook_type database table.
 * 
 */
@Entity
@Table(name="ebook_type")
@NamedQuery(name="EbookType.findAll", query="SELECT e FROM EbookType e")
public class EbookType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int typeId;

	@Lob
	@Column(nullable=false)
	private String description;

	@Column(nullable=false)
	private int display;

	@Column(nullable=false)
	private int photoHeight;

	@Column(nullable=false)
	private int photoWidth;

	@Column(nullable=false, length=45)
	private String title;

	public EbookType() {
	}

	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}