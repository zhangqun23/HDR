package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the material_sort_finance database table.
 * 
 */
@Entity
@Table(name="material_sort_finance")
@NamedQuery(name="MaterialSortFinance.findAll", query="SELECT m FROM MaterialSortFinance m")
public class MaterialSortFinance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fsort_id", unique=true, nullable=false)
	private int fsortId;

	@Column(name="fsort_name", length=15)
	private String fsortName;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	public MaterialSortFinance() {
	}

	public int getFsortId() {
		return this.fsortId;
	}

	public void setFsortId(int fsortId) {
		this.fsortId = fsortId;
	}

	public String getFsortName() {
		return this.fsortName;
	}

	public void setFsortName(String fsortName) {
		this.fsortName = fsortName;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

}