package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contact_info database table.
 * 
 */
@Entity
@Table(name="contact_info")
@NamedQuery(name="ContactInfo.findAll", query="SELECT c FROM ContactInfo c")
public class ContactInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="contact_id", unique=true, nullable=false)
	private int contactId;

	@Column(name="department_id", length=16)
	private String departmentId;

	@Column(name="direct_line", length=16)
	private String directLine;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="office_area", length=32)
	private String officeArea;

	@Column(name="office_tel", length=16)
	private String officeTel;

	@Column(length=255)
	private String remark;

	@Column(name="staff_id")
	private int staffId;

	@Column(name="staff_tel2", length=16)
	private String staffTel2;

	@Column(name="wireless_tel", length=16)
	private String wirelessTel;

	public ContactInfo() {
	}

	public int getContactId() {
		return this.contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDirectLine() {
		return this.directLine;
	}

	public void setDirectLine(String directLine) {
		this.directLine = directLine;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getOfficeArea() {
		return this.officeArea;
	}

	public void setOfficeArea(String officeArea) {
		this.officeArea = officeArea;
	}

	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffTel2() {
		return this.staffTel2;
	}

	public void setStaffTel2(String staffTel2) {
		this.staffTel2 = staffTel2;
	}

	public String getWirelessTel() {
		return this.wirelessTel;
	}

	public void setWirelessTel(String wirelessTel) {
		this.wirelessTel = wirelessTel;
	}

}