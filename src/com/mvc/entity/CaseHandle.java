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

	private Integer recordId;// ID
	private CaseInfo caseInfo;// 对应case_info中的case_id
	private String handleMessage;// 消息处理具体描述
	private String recordSort;// 记录类型，新建任务、接受任务、取消任务、关闭任务、创建查房
	private Date recordTime;// 记录时间
	private String recorderId;// 记录人ID，对应staff_info中的staff_id
	private String remark;// 备注信息

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