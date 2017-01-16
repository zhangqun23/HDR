package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the alert_role_rela database table.
 * 
 */
@Entity
@Table(name="alert_role_rela")
@NamedQuery(name="AlertRoleRela.findAll", query="SELECT a FROM AlertRoleRela a")
public class AlertRoleRela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="alert_model_id")
	private int alertModelId;

	@Column(name="receiver_id")
	private int receiverId;

	@Column(name="role_id")
	private int roleId;

	public AlertRoleRela() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAlertModelId() {
		return this.alertModelId;
	}

	public void setAlertModelId(int alertModelId) {
		this.alertModelId = alertModelId;
	}

	public int getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}