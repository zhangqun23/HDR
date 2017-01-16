package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the model_info database table.
 * 
 */
@Entity
@Table(name="model_info")
@NamedQuery(name="ModelInfo.findAll", query="SELECT m FROM ModelInfo m")
public class ModelInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=16)
	private String model_id;

	@Column(nullable=false, length=32)
	private String model_name;

	@Column(nullable=false, length=16)
	private String parent_id;

	public ModelInfo() {
	}

	public String getModel_id() {
		return this.model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getModel_name() {
		return this.model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

}