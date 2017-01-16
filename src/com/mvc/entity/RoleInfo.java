package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_info database table.
 * 
 */
@Entity
@Table(name="role_info")
@NamedQuery(name="RoleInfo.findAll", query="SELECT r FROM RoleInfo r")
public class RoleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int role_id;

	@Column(nullable=false, length=16)
	private String model_id;

	@Column(nullable=false, length=16)
	private String role_name;

	@Column(length=128)
	private String role_note;

	public RoleInfo() {
	}

	public int getRole_id() {
		return this.role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getModel_id() {
		return this.model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getRole_name() {
		return this.role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_note() {
		return this.role_note;
	}

	public void setRole_note(String role_note) {
		this.role_note = role_note;
	}

}