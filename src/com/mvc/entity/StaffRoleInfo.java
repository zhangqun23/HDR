package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_role_info database table.
 * 
 */
@Entity
@Table(name="staff_role_info")
@NamedQuery(name="StaffRoleInfo.findAll", query="SELECT s FROM StaffRoleInfo s")
public class StaffRoleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id", unique=true, nullable=false)
	private int roleId;

	@Column(name="is_receive_checkroom", nullable=false)
	private int isReceiveCheckroom;

	@Column(name="is_routine_checker", nullable=false)
	private int isRoutineChecker;

	@Column(name="is_servant", nullable=false)
	private byte isServant;

	@Column(name="is_use_pda", nullable=false)
	private byte isUsePda;

	@Column(name="role_dep_id", length=40)
	private String roleDepId;

	@Column(name="role_name", length=10)
	private String roleName;

	@Column(name="role_rank", nullable=false)
	private byte roleRank;

	public StaffRoleInfo() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getIsReceiveCheckroom() {
		return this.isReceiveCheckroom;
	}

	public void setIsReceiveCheckroom(int isReceiveCheckroom) {
		this.isReceiveCheckroom = isReceiveCheckroom;
	}

	public int getIsRoutineChecker() {
		return this.isRoutineChecker;
	}

	public void setIsRoutineChecker(int isRoutineChecker) {
		this.isRoutineChecker = isRoutineChecker;
	}

	public byte getIsServant() {
		return this.isServant;
	}

	public void setIsServant(byte isServant) {
		this.isServant = isServant;
	}

	public byte getIsUsePda() {
		return this.isUsePda;
	}

	public void setIsUsePda(byte isUsePda) {
		this.isUsePda = isUsePda;
	}

	public String getRoleDepId() {
		return this.roleDepId;
	}

	public void setRoleDepId(String roleDepId) {
		this.roleDepId = roleDepId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public byte getRoleRank() {
		return this.roleRank;
	}

	public void setRoleRank(byte roleRank) {
		this.roleRank = roleRank;
	}

}