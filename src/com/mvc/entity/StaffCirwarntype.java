package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_cirwarntype database table.
 * 
 */
@Entity
@Table(name="staff_cirwarntype")
@NamedQuery(name="StaffCirwarntype.findAll", query="SELECT s FROM StaffCirwarntype s")
public class StaffCirwarntype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String is_delete;

	@Column(length=255)
	private String warn_Id;

	@Column(length=255)
	private String warn_Weekormonth;

	public StaffCirwarntype() {
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

	public String getWarn_Id() {
		return this.warn_Id;
	}

	public void setWarn_Id(String warn_Id) {
		this.warn_Id = warn_Id;
	}

	public String getWarn_Weekormonth() {
		return this.warn_Weekormonth;
	}

	public void setWarn_Weekormonth(String warn_Weekormonth) {
		this.warn_Weekormonth = warn_Weekormonth;
	}

}