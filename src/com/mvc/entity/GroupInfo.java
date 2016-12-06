package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the group_info database table.
 * 
 */
@Entity
@Table(name="group_info")
@NamedQuery(name="GroupInfo.findAll", query="SELECT g FROM GroupInfo g")
public class GroupInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="group_id", unique=true, nullable=false)
	private int groupId;

	@Column(name="group_name", length=255)
	private String groupName;

	@Column(nullable=false)
	private byte isdeleted;

	@Column(name="staff_id")
	private int staffId;

	public GroupInfo() {
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public byte getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(byte isdeleted) {
		this.isdeleted = isdeleted;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

}