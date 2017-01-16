package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_pda_rela database table.
 * 
 */
@Entity
@Table(name="staff_pda_rela")
@NamedQuery(name="StaffPdaRela.findAll", query="SELECT s FROM StaffPdaRela s")
public class StaffPdaRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=16)
	private String PDA_id;

	@Column(nullable=false)
	private int staff_id;

	public StaffPdaRela() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPDA_id() {
		return this.PDA_id;
	}

	public void setPDA_id(String PDA_id) {
		this.PDA_id = PDA_id;
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

}