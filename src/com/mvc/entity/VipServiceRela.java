package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vip_service_rela database table.
 * 
 */
@Entity
@Table(name="vip_service_rela")
@NamedQuery(name="VipServiceRela.findAll", query="SELECT v FROM VipServiceRela v")
public class VipServiceRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VipServiceRelaPK id;

	public VipServiceRela() {
	}

	public VipServiceRelaPK getId() {
		return this.id;
	}

	public void setId(VipServiceRelaPK id) {
		this.id = id;
	}

}