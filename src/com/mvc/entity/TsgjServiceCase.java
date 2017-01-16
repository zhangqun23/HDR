package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tsgj_service_case database table.
 * 
 */
@Entity
@Table(name="tsgj_service_case")
@NamedQuery(name="TsgjServiceCase.findAll", query="SELECT t FROM TsgjServiceCase t")
public class TsgjServiceCase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="case_id", length=20)
	private String caseId;

	@Column(name="check_flag", nullable=false)
	private int checkFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="close_time")
	private Date closeTime;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="customer_id", nullable=false, length=25)
	private String customerId;

	@Column(name="room_no", nullable=false, length=15)
	private String roomNo;

	@Column(name="service_id")
	private int serviceId;

	@Column(name="update_flag", nullable=false)
	private int updateFlag;

	public TsgjServiceCase() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public int getCheckFlag() {
		return this.checkFlag;
	}

	public void setCheckFlag(int checkFlag) {
		this.checkFlag = checkFlag;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getUpdateFlag() {
		return this.updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

}