package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the equip_notes database table.
 * 
 */
@Entity
@Table(name="equip_notes")
@NamedQuery(name="EquipNote.findAll", query="SELECT e FROM EquipNote e")
public class EquipNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="record_id", unique=true, nullable=false)
	private int recordId;

	@Column(name="equip_id", nullable=false, length=16)
	private String equipId;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="notes_message", nullable=false, length=255)
	private String notesMessage;

	@Temporal(TemporalType.DATE)
	@Column(name="notes_time")
	private Date notesTime;

	@Column(name="notes_value", nullable=false, length=32)
	private String notesValue;

	public EquipNote() {
	}

	public int getRecordId() {
		return this.recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getEquipId() {
		return this.equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getNotesMessage() {
		return this.notesMessage;
	}

	public void setNotesMessage(String notesMessage) {
		this.notesMessage = notesMessage;
	}

	public Date getNotesTime() {
		return this.notesTime;
	}

	public void setNotesTime(Date notesTime) {
		this.notesTime = notesTime;
	}

	public String getNotesValue() {
		return this.notesValue;
	}

	public void setNotesValue(String notesValue) {
		this.notesValue = notesValue;
	}

}