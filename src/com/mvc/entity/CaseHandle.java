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

	private Integer recordId;
	private CaseInfo caseInfo;
	private String handleMessage;
	private String recordSort;
	private Date recordTime;
	private String recorderId;
	private String remark;

	public CaseHandle() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "record_id", unique = true, nullable = false)
	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	@ManyToOne
	@JoinColumn(name = "case_id")
	public CaseInfo getCaseInfo() {
		return caseInfo;
	}

	public void setCaseInfo(CaseInfo caseInfo) {
		this.caseInfo = caseInfo;
	}

	@Lob
	@Column(name = "handle_message")
	public String getHandleMessage() {
		return this.handleMessage;
	}

	public void setHandleMessage(String handleMessage) {
		this.handleMessage = handleMessage;
	}

	@Column(name = "record_sort", nullable = false, length = 16)
	public String getRecordSort() {
		return this.recordSort;
	}

	public void setRecordSort(String recordSort) {
		this.recordSort = recordSort;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "record_time", nullable = false)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "recorder_id", nullable = false, length = 16)
	public String getRecorderId() {
		return this.recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	@Column(length = 255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}