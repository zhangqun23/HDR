package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the exchange_rate database table.
 * 
 */
@Entity
@Table(name="exchange_rate")
@NamedQuery(name="ExchangeRate.findAll", query="SELECT e FROM ExchangeRate e")
public class ExchangeRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=45)
	private String basePrice;

	@Column(name="exchange_unit", nullable=false, length=45)
	private String exchangeUnit;

	@Column(nullable=false, length=45)
	private String fBuyPrice;

	@Column(nullable=false, length=45)
	private String mBuyPrice;

	@Column(nullable=false)
	private int orderNum;

	@Column(name="rate_name", nullable=false, length=45)
	private String rateName;

	@Column(nullable=false, length=45)
	private String sellPrice;

	@Column(nullable=false, length=45)
	private String symbol;

	public ExchangeRate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}

	public String getExchangeUnit() {
		return this.exchangeUnit;
	}

	public void setExchangeUnit(String exchangeUnit) {
		this.exchangeUnit = exchangeUnit;
	}

	public String getFBuyPrice() {
		return this.fBuyPrice;
	}

	public void setFBuyPrice(String fBuyPrice) {
		this.fBuyPrice = fBuyPrice;
	}

	public String getMBuyPrice() {
		return this.mBuyPrice;
	}

	public void setMBuyPrice(String mBuyPrice) {
		this.mBuyPrice = mBuyPrice;
	}

	public int getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getRateName() {
		return this.rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public String getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}