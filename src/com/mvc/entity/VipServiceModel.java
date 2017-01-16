package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vip_service_model database table.
 * 
 */
@Entity
@Table(name="vip_service_model")
@NamedQuery(name="VipServiceModel.findAll", query="SELECT v FROM VipServiceModel v")
public class VipServiceModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="model_id", unique=true, nullable=false)
	private int modelId;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="model_content", nullable=false, length=255)
	private String modelContent;

	@Column(name="notice_flag", nullable=false, length=1)
	private String noticeFlag;

	@Column(name="vip_rank", nullable=false, length=20)
	private String vipRank;

	public VipServiceModel() {
	}

	public int getModelId() {
		return this.modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getModelContent() {
		return this.modelContent;
	}

	public void setModelContent(String modelContent) {
		this.modelContent = modelContent;
	}

	public String getNoticeFlag() {
		return this.noticeFlag;
	}

	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public String getVipRank() {
		return this.vipRank;
	}

	public void setVipRank(String vipRank) {
		this.vipRank = vipRank;
	}

}