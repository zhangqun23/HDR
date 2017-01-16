package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vip_model_receiver database table.
 * 
 */
@Entity
@Table(name="vip_model_receiver")
@NamedQuery(name="VipModelReceiver.findAll", query="SELECT v FROM VipModelReceiver v")
public class VipModelReceiver implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VipModelReceiverPK id;

	public VipModelReceiver() {
	}

	public VipModelReceiverPK getId() {
		return this.id;
	}

	public void setId(VipModelReceiverPK id) {
		this.id = id;
	}

}