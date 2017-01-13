package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the customer_info_more database table.
 * 
 */
@Entity
@Table(name="customer_info_more")
@NamedQuery(name="CustomerInfoMore.findAll", query="SELECT c FROM CustomerInfoMore c")
public class CustomerInfoMore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id", unique=true, nullable=false, length=16)
	private String customerId;

	@Column(length=10)
	private String adult;

	@Column(length=20)
	private String arr;

	@Column(length=255)
	private String arrtime;

	@Column(length=20)
	private String birth;

	@Column(length=20)
	private String block;

	@Column(length=20)
	private String bodystyle;

	@Column(length=20)
	private String bookcenter;

	@Column(length=20)
	private String booktype;

	@Column(nullable=false, length=1)
	private String callflag;

	@Column(length=255)
	private String car;

	@Column(length=20)
	private String cardno;

	@Column(length=10)
	private String cardtype;

	@Column(length=20)
	private String company;

	@Column(length=10)
	private String days;

	@Column(length=20)
	private String dep;

	@Column(length=255)
	private String deptime;

	@Column(length=30)
	private String email;

	@Column(length=20)
	private String fax;

	@Column(nullable=false, length=30)
	private String fname;

	@Column(length=255)
	private String from;

	@Column(name="goto", length=255)
	private String goto_;

	@Column(length=20)
	private String group;

	@Column(nullable=false, length=3)
	private String idcls;

	@Column(nullable=false, length=20)
	private String idend;

	@Column(nullable=false, length=20)
	private String ident;

	@Column(length=20)
	private String jiguan;

	@Column(length=10)
	private String kid;

	@Column(nullable=false, length=10)
	private String lang;

	@Column(length=50)
	private String like;

	@Column(length=20)
	private String likeroom;

	@Column(nullable=false, length=30)
	private String lname;

	@Column(nullable=false, length=20)
	private String name;

	@Column(nullable=false, length=50)
	private String name2;

	@Column(nullable=false, length=10)
	private String nation;

	@Column(length=20)
	private String phone;

	@Column(nullable=false, length=10)
	private String race;

	@Column(length=255)
	private String remark;

	@Column(length=10)
	private String roomcode;

	@Column(length=50)
	private String roomdecor;

	@Column(length=20)
	private String roomdemand;

	@Column(length=255)
	private String roomno;

	@Column(length=10)
	private String roomnum;

	@Column(length=255)
	private String roomsort;

	@Column(length=255)
	private String roomupdate;

	@Column(nullable=false, length=10)
	private String sex;

	@Column(length=50)
	private String specialdemand;

	@Column(length=255)
	private String street;

	@Column(length=10)
	private String title;

	@Column(length=20)
	private String travelcompany;

	@Column(length=255)
	private String updatereason;

	@Column(nullable=false, length=10)
	private String vip;

	public CustomerInfoMore() {
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAdult() {
		return this.adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getArr() {
		return this.arr;
	}

	public void setArr(String arr) {
		this.arr = arr;
	}

	public String getArrtime() {
		return this.arrtime;
	}

	public void setArrtime(String arrtime) {
		this.arrtime = arrtime;
	}

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getBlock() {
		return this.block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getBodystyle() {
		return this.bodystyle;
	}

	public void setBodystyle(String bodystyle) {
		this.bodystyle = bodystyle;
	}

	public String getBookcenter() {
		return this.bookcenter;
	}

	public void setBookcenter(String bookcenter) {
		this.bookcenter = bookcenter;
	}

	public String getBooktype() {
		return this.booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public String getCallflag() {
		return this.callflag;
	}

	public void setCallflag(String callflag) {
		this.callflag = callflag;
	}

	public String getCar() {
		return this.car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDays() {
		return this.days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getDep() {
		return this.dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getDeptime() {
		return this.deptime;
	}

	public void setDeptime(String deptime) {
		this.deptime = deptime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getGoto_() {
		return this.goto_;
	}

	public void setGoto_(String goto_) {
		this.goto_ = goto_;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getIdcls() {
		return this.idcls;
	}

	public void setIdcls(String idcls) {
		this.idcls = idcls;
	}

	public String getIdend() {
		return this.idend;
	}

	public void setIdend(String idend) {
		this.idend = idend;
	}

	public String getIdent() {
		return this.ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getJiguan() {
		return this.jiguan;
	}

	public void setJiguan(String jiguan) {
		this.jiguan = jiguan;
	}

	public String getKid() {
		return this.kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLike() {
		return this.like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getLikeroom() {
		return this.likeroom;
	}

	public void setLikeroom(String likeroom) {
		this.likeroom = likeroom;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRace() {
		return this.race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoomcode() {
		return this.roomcode;
	}

	public void setRoomcode(String roomcode) {
		this.roomcode = roomcode;
	}

	public String getRoomdecor() {
		return this.roomdecor;
	}

	public void setRoomdecor(String roomdecor) {
		this.roomdecor = roomdecor;
	}

	public String getRoomdemand() {
		return this.roomdemand;
	}

	public void setRoomdemand(String roomdemand) {
		this.roomdemand = roomdemand;
	}

	public String getRoomno() {
		return this.roomno;
	}

	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}

	public String getRoomnum() {
		return this.roomnum;
	}

	public void setRoomnum(String roomnum) {
		this.roomnum = roomnum;
	}

	public String getRoomsort() {
		return this.roomsort;
	}

	public void setRoomsort(String roomsort) {
		this.roomsort = roomsort;
	}

	public String getRoomupdate() {
		return this.roomupdate;
	}

	public void setRoomupdate(String roomupdate) {
		this.roomupdate = roomupdate;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSpecialdemand() {
		return this.specialdemand;
	}

	public void setSpecialdemand(String specialdemand) {
		this.specialdemand = specialdemand;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTravelcompany() {
		return this.travelcompany;
	}

	public void setTravelcompany(String travelcompany) {
		this.travelcompany = travelcompany;
	}

	public String getUpdatereason() {
		return this.updatereason;
	}

	public void setUpdatereason(String updatereason) {
		this.updatereason = updatereason;
	}

	public String getVip() {
		return this.vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

}