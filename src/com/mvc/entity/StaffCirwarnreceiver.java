package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_cirwarnreceiver database table.
 * 
 */
@Entity
@Table(name="staff_cirwarnreceiver")
@NamedQuery(name="StaffCirwarnreceiver.findAll", query="SELECT s FROM StaffCirwarnreceiver s")
public class StaffCirwarnreceiver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String is_delete;

	@Column(length=255)
	private String staff_Id;

	@Column(length=255)
	private String warn_Id;

	public StaffCirwarnreceiver() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIs_delete() {
		return this.is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	public String getStaff_Id() {
		return this.staff_Id;
	}

	public void setStaff_Id(String staff_Id) {
		this.staff_Id = staff_Id;
	}

	public String getWarn_Id() {
		return this.warn_Id;
	}

	public void setWarn_Id(String warn_Id) {
		this.warn_Id = warn_Id;
	}

}