package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the vip_model_receiver database table.
 * 
 */
@Embeddable
public class VipModelReceiverPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="model_id", unique=true, nullable=false)
	private int modelId;

	@Column(name="receiver_id", unique=true, nullable=false)
	private int receiverId;

	public VipModelReceiverPK() {
	}
	public int getModelId() {
		return this.modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getReceiverId() {
		return this.receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VipModelReceiverPK)) {
			return false;
		}
		VipModelReceiverPK castOther = (VipModelReceiverPK)other;
		return 
			(this.modelId == castOther.modelId)
			&& (this.receiverId == castOther.receiverId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.modelId;
		hash = hash * prime + this.receiverId;
		
		return hash;
	}
}