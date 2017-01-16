package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the duty_schedule database table.
 * 
 */
@Entity
@Table(name="duty_schedule")
@NamedQuery(name="DutySchedule.findAll", query="SELECT d FROM DutySchedule d")
public class DutySchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=8)
	private String arrange_duty;

	@Column(nullable=false)
	private int arrange_person;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date arrange_time;

	@Column(nullable=false, length=16)
	private String department_id;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date duty_date;

	@Column(name="hour_templeave")
	private int hourTempleave;

	@Column(nullable=false)
	private int is_templeave;

	@Column(nullable=false, length=16)
	private String staff_no;

	private Time startime_templeave;

	private int time_downextrawork;

	private int time_downlackofwork;

	private int time_upextrawork;

	private int time_uplackofwork;

	public DutySchedule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArrange_duty() {
		return this.arrange_duty;
	}

	public void setArrange_duty(String arrange_duty) {
		this.arrange_duty = arrange_duty;
	}

	public int getArrange_person() {
		return this.arrange_person;
	}

	public void setArrange_person(int arrange_person) {
		this.arrange_person = arrange_person;
	}

	public Date getArrange_time() {
		return this.arrange_time;
	}

	public void setArrange_time(Date arrange_time) {
		this.arrange_time = arrange_time;
	}

	public String getDepartment_id() {
		return this.department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public Date getDuty_date() {
		return this.duty_date;
	}

	public void setDuty_date(Date duty_date) {
		this.duty_date = duty_date;
	}

	public int getHourTempleave() {
		return this.hourTempleave;
	}

	public void setHourTempleave(int hourTempleave) {
		this.hourTempleave = hourTempleave;
	}

	public int getIs_templeave() {
		return this.is_templeave;
	}

	public void setIs_templeave(int is_templeave) {
		this.is_templeave = is_templeave;
	}

	public String getStaff_no() {
		return this.staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public Time getStartime_templeave() {
		return this.startime_templeave;
	}

	public void setStartime_templeave(Time startime_templeave) {
		this.startime_templeave = startime_templeave;
	}

	public int getTime_downextrawork() {
		return this.time_downextrawork;
	}

	public void setTime_downextrawork(int time_downextrawork) {
		this.time_downextrawork = time_downextrawork;
	}

	public int getTime_downlackofwork() {
		return this.time_downlackofwork;
	}

	public void setTime_downlackofwork(int time_downlackofwork) {
		this.time_downlackofwork = time_downlackofwork;
	}

	public int getTime_upextrawork() {
		return this.time_upextrawork;
	}

	public void setTime_upextrawork(int time_upextrawork) {
		this.time_upextrawork = time_upextrawork;
	}

	public int getTime_uplackofwork() {
		return this.time_uplackofwork;
	}

	public void setTime_uplackofwork(int time_uplackofwork) {
		this.time_uplackofwork = time_uplackofwork;
	}

}