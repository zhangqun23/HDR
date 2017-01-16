package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_role_rela database table.
 * 
 */
@Entity
@Table(name="staff_role_rela")
@NamedQuery(name="StaffRoleRela.findAll", query="SELECT s FROM StaffRoleRela s")
public class StaffRoleRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=16)
	private String model_id;

	private int role_rank;

	private int staff_id;

	public StaffRoleRela() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel_id() {
		return this.model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public int getRole_rank() {
		return this.role_rank;
	}

	public void setRole_rank(int role_rank) {
		this.role_rank = role_rank;
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

}