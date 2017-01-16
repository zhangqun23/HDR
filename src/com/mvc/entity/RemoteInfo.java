package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the remote_info database table.
 * 
 */
@Entity
@Table(name="remote_info")
@NamedQuery(name="RemoteInfo.findAll", query="SELECT r FROM RemoteInfo r")
public class RemoteInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=16)
	private String remote_id;

	@Column(length=128)
	private String remote_note;

	@Column(length=16)
	private String remote_states;

	public RemoteInfo() {
	}

	public String getRemote_id() {
		return this.remote_id;
	}

	public void setRemote_id(String remote_id) {
		this.remote_id = remote_id;
	}

	public String getRemote_note() {
		return this.remote_note;
	}

	public void setRemote_note(String remote_note) {
		this.remote_note = remote_note;
	}

	public String getRemote_states() {
		return this.remote_states;
	}

	public void setRemote_states(String remote_states) {
		this.remote_states = remote_states;
	}

}