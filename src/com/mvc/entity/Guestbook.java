package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the guestbook database table.
 * 
 */
@Entity
@Table(name="guestbook")
@NamedQuery(name="Guestbook.findAll", query="SELECT g FROM Guestbook g")
public class Guestbook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String address;

	@Column(length=25)
	private String arrival;

	@Column(length=255)
	private String city;

	@Column(length=255)
	private String country;

	@Column(nullable=false)
	private Timestamp creatTime;

	@Column(length=255)
	private String email;

	@Column(length=255)
	private String food;

	@Column(length=255)
	private String name;

	@Lob
	private String overall;

	@Column(length=255)
	private String phone;

	@Column(length=255)
	private String postal;

	@Column(length=255)
	private String room;

	@Column(name="room_no", length=25)
	private String roomNo;

	@Column(length=255)
	private String staff;

	@Column(length=255)
	private String suggestion;

	public Guestbook() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArrival() {
		return this.arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getCreatTime() {
		return this.creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFood() {
		return this.food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOverall() {
		return this.overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostal() {
		return this.postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getStaff() {
		return this.staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getSuggestion() {
		return this.suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

}