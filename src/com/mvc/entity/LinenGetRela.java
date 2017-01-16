package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the linen_get_rela database table.
 * 
 */
@Entity
@Table(name="linen_get_rela")
@NamedQuery(name="LinenGetRela.findAll", query="SELECT l FROM LinenGetRela l")
public class LinenGetRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="get_id", unique=true, nullable=false)
	private int getId;

	@Column(name="get_amount", nullable=false)
	private int getAmount;

	@Column(name="linen_id", length=20)
	private String linenId;

	@Column(name="mark_id", length=20)
	private String markId;

	public LinenGetRela() {
	}

	public int getGetId() {
		return this.getId;
	}

	public void setGetId(int getId) {
		this.getId = getId;
	}

	public int getGetAmount() {
		return this.getAmount;
	}

	public void setGetAmount(int getAmount) {
		this.getAmount = getAmount;
	}

	public String getLinenId() {
		return this.linenId;
	}

	public void setLinenId(String linenId) {
		this.linenId = linenId;
	}

	public String getMarkId() {
		return this.markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

}