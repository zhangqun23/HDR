package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the charge_info_type database table.
 * 
 */
@Entity
@Table(name="charge_info_type")
@NamedQuery(name="ChargeInfoType.findAll", query="SELECT c FROM ChargeInfoType c")
public class ChargeInfoType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=10)
	private String typeId;

	@Column(nullable=false, length=45)
	private String typeName;

	public ChargeInfoType() {
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}