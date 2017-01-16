package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the guests_comments database table.
 * 
 */
@Entity
@Table(name="guests_comments")
@NamedQuery(name="GuestsComment.findAll", query="SELECT g FROM GuestsComment g")
public class GuestsComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int comment_id;

	@Column(length=30)
	private String address;

	@Column(nullable=false, length=30)
	private String customer_name;

	@Column(nullable=false, length=30)
	private String date;

	private int food_hyg;

	private int food_quality;

	private int food_service;

	@Column(length=50)
	private String group_name;

	private int lobby_bellmen;

	private int lobby_check;

	private int lobby_info;

	private int lobby_lavatory;

	private int lobby_tel;

	@Column(length=24)
	private String nationality;

	private int other_business;

	private int other_health;

	private int other_impression;

	private int other_night;

	private int other_nightm;

	private int other_safety;

	private int other_shop;

	@Column(length=30)
	private String reserve3;

	private int room_fac;

	private int room_hyg;

	private int room_lavafac;

	private int room_lavahyg;

	@Column(nullable=false, length=10)
	private String room_no;

	private int room_service;

	private int room_valet;

	@Column(length=150)
	private String suggestion;

	public GuestsComment() {
	}

	public int getComment_id() {
		return this.comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustomer_name() {
		return this.customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getFood_hyg() {
		return this.food_hyg;
	}

	public void setFood_hyg(int food_hyg) {
		this.food_hyg = food_hyg;
	}

	public int getFood_quality() {
		return this.food_quality;
	}

	public void setFood_quality(int food_quality) {
		this.food_quality = food_quality;
	}

	public int getFood_service() {
		return this.food_service;
	}

	public void setFood_service(int food_service) {
		this.food_service = food_service;
	}

	public String getGroup_name() {
		return this.group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public int getLobby_bellmen() {
		return this.lobby_bellmen;
	}

	public void setLobby_bellmen(int lobby_bellmen) {
		this.lobby_bellmen = lobby_bellmen;
	}

	public int getLobby_check() {
		return this.lobby_check;
	}

	public void setLobby_check(int lobby_check) {
		this.lobby_check = lobby_check;
	}

	public int getLobby_info() {
		return this.lobby_info;
	}

	public void setLobby_info(int lobby_info) {
		this.lobby_info = lobby_info;
	}

	public int getLobby_lavatory() {
		return this.lobby_lavatory;
	}

	public void setLobby_lavatory(int lobby_lavatory) {
		this.lobby_lavatory = lobby_lavatory;
	}

	public int getLobby_tel() {
		return this.lobby_tel;
	}

	public void setLobby_tel(int lobby_tel) {
		this.lobby_tel = lobby_tel;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getOther_business() {
		return this.other_business;
	}

	public void setOther_business(int other_business) {
		this.other_business = other_business;
	}

	public int getOther_health() {
		return this.other_health;
	}

	public void setOther_health(int other_health) {
		this.other_health = other_health;
	}

	public int getOther_impression() {
		return this.other_impression;
	}

	public void setOther_impression(int other_impression) {
		this.other_impression = other_impression;
	}

	public int getOther_night() {
		return this.other_night;
	}

	public void setOther_night(int other_night) {
		this.other_night = other_night;
	}

	public int getOther_nightm() {
		return this.other_nightm;
	}

	public void setOther_nightm(int other_nightm) {
		this.other_nightm = other_nightm;
	}

	public int getOther_safety() {
		return this.other_safety;
	}

	public void setOther_safety(int other_safety) {
		this.other_safety = other_safety;
	}

	public int getOther_shop() {
		return this.other_shop;
	}

	public void setOther_shop(int other_shop) {
		this.other_shop = other_shop;
	}

	public String getReserve3() {
		return this.reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public int getRoom_fac() {
		return this.room_fac;
	}

	public void setRoom_fac(int room_fac) {
		this.room_fac = room_fac;
	}

	public int getRoom_hyg() {
		return this.room_hyg;
	}

	public void setRoom_hyg(int room_hyg) {
		this.room_hyg = room_hyg;
	}

	public int getRoom_lavafac() {
		return this.room_lavafac;
	}

	public void setRoom_lavafac(int room_lavafac) {
		this.room_lavafac = room_lavafac;
	}

	public int getRoom_lavahyg() {
		return this.room_lavahyg;
	}

	public void setRoom_lavahyg(int room_lavahyg) {
		this.room_lavahyg = room_lavahyg;
	}

	public String getRoom_no() {
		return this.room_no;
	}

	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}

	public int getRoom_service() {
		return this.room_service;
	}

	public void setRoom_service(int room_service) {
		this.room_service = room_service;
	}

	public int getRoom_valet() {
		return this.room_valet;
	}

	public void setRoom_valet(int room_valet) {
		this.room_valet = room_valet;
	}

	public String getSuggestion() {
		return this.suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

}