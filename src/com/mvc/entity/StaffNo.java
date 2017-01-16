package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_no database table.
 * 
 */
@Entity
@Table(name="staff_no")
@NamedQuery(name="StaffNo.findAll", query="SELECT s FROM StaffNo s")
public class StaffNo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=16)
	private String staff_no;

	private int isUsed;

	public StaffNo() {
	}

	public String getStaff_no() {
		return this.staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public int getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

}