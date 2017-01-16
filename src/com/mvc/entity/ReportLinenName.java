package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the report_linen_name database table.
 * 
 */
@Entity
@Table(name="report_linen_name")
@NamedQuery(name="ReportLinenName.findAll", query="SELECT r FROM ReportLinenName r")
public class ReportLinenName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="linen_name", nullable=false, length=50)
	private String linenName;

	@Column(name="linen_sort", length=20)
	private String linenSort;

	@Column(name="show_order")
	private byte showOrder;

	public ReportLinenName() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getLinenName() {
		return this.linenName;
	}

	public void setLinenName(String linenName) {
		this.linenName = linenName;
	}

	public String getLinenSort() {
		return this.linenSort;
	}

	public void setLinenSort(String linenSort) {
		this.linenSort = linenSort;
	}

	public byte getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(byte showOrder) {
		this.showOrder = showOrder;
	}

}