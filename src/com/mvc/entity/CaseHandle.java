package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 任务处理
 * 
 * @author wangrui
 * @date 2016年12月8日
 */
@Entity
@Table(name = "case_handle")
public class CaseHandle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "record_id", unique = true, nullable = false)
	private Integer recordId;

	@ManyToOne
	@JoinColumn(name = "case_id")
	private CaseInfo caseInfo;

	@Lob
	@Column(name = "handle_message")
	private String handleMessage;

	@Column(name = "record_sort", nullable = false, length = 16)
	private String recordSort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "record_time", nullable = false)
	private Date recordTime;

	@Column(name = "recorder_id", nullable = false, length = 16)
	private String recorderId;

	@Column(length = 255)
	private String remark;

	public CaseHandle() {
	}

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public CaseInfo getCaseInfo() {
		return caseInfo;
	}

	public void setCaseInfo(CaseInfo caseInfo) {
		this.caseInfo = caseInfo;
	}

	public String getHandleMessage() {
		return this.handleMessage;
	}

	public void setHandleMessage(String handleMessage) {
		this.handleMessage = handleMessage;
	}

	public String getRecordSort() {
		return this.recordSort;
	}

	public void setRecordSort(String recordSort) {
		this.recordSort = recordSort;
	}

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}