package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the movie_sort database table.
 * 
 */
@Entity
@Table(name="movie_sort")
@NamedQuery(name="MovieSort.findAll", query="SELECT m FROM MovieSort m")
public class MovieSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable=false, length=24)
	private String mt_id;

	@Column(nullable=false, length=30)
	private String mt_name;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(length=30)
	private String reserve3;

	public MovieSort() {
	}

	public String getMt_id() {
		return this.mt_id;
	}

	public void setMt_id(String mt_id) {
		this.mt_id = mt_id;
	}

	public String getMt_name() {
		return this.mt_name;
	}

	public void setMt_name(String mt_name) {
		this.mt_name = mt_name;
	}

	public String getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return this.reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

}