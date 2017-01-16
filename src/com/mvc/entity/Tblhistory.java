package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tblhistory database table.
 * 
 */
@Entity
@Table(name="tblhistory")
@NamedQuery(name="Tblhistory.findAll", query="SELECT t FROM Tblhistory t")
public class Tblhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="his_id", unique=true, nullable=false)
	private int hisId;

	@Column(name="his_mobil", length=15)
	private String hisMobil;

	@Column(name="his_msg", length=255)
	private String hisMsg;

	@Column(name="his_name", length=20)
	private String hisName;

	@Column(name="his_oprt", length=20)
	private String hisOprt;

	@Column(name="his_return", length=10)
	private String hisReturn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="his_time")
	private Date hisTime;

	@Column(name="his_type", length=32)
	private String hisType;

	public Tblhistory() {
	}

	public int getHisId() {
		return this.hisId;
	}

	public void setHisId(int hisId) {
		this.hisId = hisId;
	}

	public String getHisMobil() {
		return this.hisMobil;
	}

	public void setHisMobil(String hisMobil) {
		this.hisMobil = hisMobil;
	}

	public String getHisMsg() {
		return this.hisMsg;
	}

	public void setHisMsg(String hisMsg) {
		this.hisMsg = hisMsg;
	}

	public String getHisName() {
		return this.hisName;
	}

	public void setHisName(String hisName) {
		this.hisName = hisName;
	}

	public String getHisOprt() {
		return this.hisOprt;
	}

	public void setHisOprt(String hisOprt) {
		this.hisOprt = hisOprt;
	}

	public String getHisReturn() {
		return this.hisReturn;
	}

	public void setHisReturn(String hisReturn) {
		this.hisReturn = hisReturn;
	}

	public Date getHisTime() {
		return this.hisTime;
	}

	public void setHisTime(Date hisTime) {
		this.hisTime = hisTime;
	}

	public String getHisType() {
		return this.hisType;
	}

	public void setHisType(String hisType) {
		this.hisType = hisType;
	}

}