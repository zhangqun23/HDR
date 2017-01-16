package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goods_with database table.
 * 
 */
@Entity
@Table(name="goods_with")
@NamedQuery(name="GoodsWith.findAll", query="SELECT g FROM GoodsWith g")
public class GoodsWith implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int withId;

	@Lob
	private String description;

	@Column(nullable=false)
	private int display;

	@Column(name="goods_id", nullable=false)
	private int goodsId;

	@Column(nullable=false, length=30)
	private String price;

	@Column(nullable=false, length=45)
	private String withEname;

	@Column(nullable=false, length=60)
	private String withName;

	public GoodsWith() {
	}

	public int getWithId() {
		return this.withId;
	}

	public void setWithId(int withId) {
		this.withId = withId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getWithEname() {
		return this.withEname;
	}

	public void setWithEname(String withEname) {
		this.withEname = withEname;
	}

	public String getWithName() {
		return this.withName;
	}

	public void setWithName(String withName) {
		this.withName = withName;
	}

}