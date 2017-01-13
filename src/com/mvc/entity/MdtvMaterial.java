package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mdtv_material database table.
 * 
 */
@Entity
@Table(name="mdtv_material")
@NamedQuery(name="MdtvMaterial.findAll", query="SELECT m FROM MdtvMaterial m")
public class MdtvMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int mId;

	@Column(nullable=false)
	private byte display;

	@Column(nullable=false, length=200)
	private String eTitle;

	@Column(nullable=false)
	private int fatherId;

	@Column(nullable=false)
	private int fileHeight;

	@Column(nullable=false, length=45)
	private String fileName;

	@Column(nullable=false, length=60)
	private String filePath;

	@Column(nullable=false)
	private int fileWidth;

	private int hasPic;

	@Column(nullable=false)
	private byte hasSubMenu;

	@Column(nullable=false, length=45)
	private String menuType;

	@Column(nullable=false)
	private int paramIntValue;

	@Column(length=200)
	private String paramStringValue;

	@Column(length=100)
	private String payInfo;

	@Column(nullable=false)
	private int serviceId;

	@Column(nullable=false)
	private int sort;

	@Column(nullable=false, length=200)
	private String title;

	@Column(nullable=false)
	private Timestamp updateTime;

	public MdtvMaterial() {
	}

	public int getMId() {
		return this.mId;
	}

	public void setMId(int mId) {
		this.mId = mId;
	}

	public byte getDisplay() {
		return this.display;
	}

	public void setDisplay(byte display) {
		this.display = display;
	}

	public String getETitle() {
		return this.eTitle;
	}

	public void setETitle(String eTitle) {
		this.eTitle = eTitle;
	}

	public int getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}

	public int getFileHeight() {
		return this.fileHeight;
	}

	public void setFileHeight(int fileHeight) {
		this.fileHeight = fileHeight;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileWidth() {
		return this.fileWidth;
	}

	public void setFileWidth(int fileWidth) {
		this.fileWidth = fileWidth;
	}

	public int getHasPic() {
		return this.hasPic;
	}

	public void setHasPic(int hasPic) {
		this.hasPic = hasPic;
	}

	public byte getHasSubMenu() {
		return this.hasSubMenu;
	}

	public void setHasSubMenu(byte hasSubMenu) {
		this.hasSubMenu = hasSubMenu;
	}

	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public int getParamIntValue() {
		return this.paramIntValue;
	}

	public void setParamIntValue(int paramIntValue) {
		this.paramIntValue = paramIntValue;
	}

	public String getParamStringValue() {
		return this.paramStringValue;
	}

	public void setParamStringValue(String paramStringValue) {
		this.paramStringValue = paramStringValue;
	}

	public String getPayInfo() {
		return this.payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}