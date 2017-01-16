package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the vip_service_rela database table.
 * 
 */
@Embeddable
public class VipServiceRelaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="model_id", unique=true, nullable=false)
	private int modelId;

	@Column(name="service_id", unique=true, nullable=false)
	private int serviceId;

	public VipServiceRelaPK() {
	}
	public int getModelId() {
		return this.modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getServiceId() {
		return this.serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VipServiceRelaPK)) {
			return false;
		}
		VipServiceRelaPK castOther = (VipServiceRelaPK)other;
		return 
			(this.modelId == castOther.modelId)
			&& (this.serviceId == castOther.serviceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.modelId;
		hash = hash * prime + this.serviceId;
		
		return hash;
	}
}