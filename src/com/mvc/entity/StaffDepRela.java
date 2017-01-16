package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_dep_rela database table.
 * 
 */
@Entity
@Table(name="staff_dep_rela")
@NamedQuery(name="StaffDepRela.findAll", query="SELECT s FROM StaffDepRela s")
public class StaffDepRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=16)
	private String department_id;

	@Column(nullable=false)
	private int staff_id;

	@Column(length=24)
	private String staff_role;

	private int staff_role_id;

	public StaffDepRela() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartment_id() {
		return this.department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public String getStaff_role() {
		return this.staff_role;
	}

	public void setStaff_role(String staff_role) {
		this.staff_role = staff_role;
	}

	public int getStaff_role_id() {
		return this.staff_role_id;
	}

	public void setStaff_role_id(int staff_role_id) {
		this.staff_role_id = staff_role_id;
	}

}