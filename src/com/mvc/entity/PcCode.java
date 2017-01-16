package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pc_code database table.
 * 
 */
@Entity
@Table(name="pc_code")
@NamedQuery(name="PcCode.findAll", query="SELECT p FROM PcCode p")
public class PcCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=45)
	private String codeId;

	@Column(nullable=false, length=45)
	private String codeName;

	public PcCode() {
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}