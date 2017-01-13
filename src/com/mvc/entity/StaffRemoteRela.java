package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_remote_rela database table.
 * 
 */
@Entity
@Table(name="staff_remote_rela")
@NamedQuery(name="StaffRemoteRela.findAll", query="SELECT s FROM StaffRemoteRela s")
public class StaffRemoteRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=16)
	private String remote_id;

	private int staff_id;

	public StaffRemoteRela() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemote_id() {
		return this.remote_id;
	}

	public void setRemote_id(String remote_id) {
		this.remote_id = remote_id;
	}

	public int getStaff_id() {
		return this.staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

}