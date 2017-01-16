package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the smit_apk database table.
 * 
 */
@Entity
@Table(name="smit_apk")
@NamedQuery(name="SmitApk.findAll", query="SELECT s FROM SmitApk s")
public class SmitApk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int apkId;

	@Column(name="apk_upload_time", nullable=false)
	private Timestamp apkUploadTime;

	@Column(nullable=false, length=50)
	private String apkFileName;

	@Column(length=200)
	private String apkRemark;

	@Column(nullable=false, length=10)
	private String apkType;

	@Column(nullable=false)
	private int apkVerInt;

	@Column(nullable=false, length=10)
	private String apkVersion;

	public SmitApk() {
	}

	public int getApkId() {
		return this.apkId;
	}

	public void setApkId(int apkId) {
		this.apkId = apkId;
	}

	public Timestamp getApkUploadTime() {
		return this.apkUploadTime;
	}

	public void setApkUploadTime(Timestamp apkUploadTime) {
		this.apkUploadTime = apkUploadTime;
	}

	public String getApkFileName() {
		return this.apkFileName;
	}

	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}

	public String getApkRemark() {
		return this.apkRemark;
	}

	public void setApkRemark(String apkRemark) {
		this.apkRemark = apkRemark;
	}

	public String getApkType() {
		return this.apkType;
	}

	public void setApkType(String apkType) {
		this.apkType = apkType;
	}

	public int getApkVerInt() {
		return this.apkVerInt;
	}

	public void setApkVerInt(int apkVerInt) {
		this.apkVerInt = apkVerInt;
	}

	public String getApkVersion() {
		return this.apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

}