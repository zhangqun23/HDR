package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pc_info database table.
 * 
 */
@Entity
@Table(name="pc_info")
@NamedQuery(name="PcInfo.findAll", query="SELECT p FROM PcInfo p")
public class PcInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pc_id", unique=true, nullable=false, length=16)
	private String pcId;

	@Column(name="pc_ip", length=16)
	private String pcIp;

	@Column(name="pc_remark", length=64)
	private String pcRemark;

	@Column(name="pc_status", length=32)
	private String pcStatus;

	public PcInfo() {
	}

	public String getPcId() {
		return this.pcId;
	}

	public void setPcId(String pcId) {
		this.pcId = pcId;
	}

	public String getPcIp() {
		return this.pcIp;
	}

	public void setPcIp(String pcIp) {
		this.pcIp = pcIp;
	}

	public String getPcRemark() {
		return this.pcRemark;
	}

	public void setPcRemark(String pcRemark) {
		this.pcRemark = pcRemark;
	}

	public String getPcStatus() {
		return this.pcStatus;
	}

	public void setPcStatus(String pcStatus) {
		this.pcStatus = pcStatus;
	}

}