package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tongtest database table.
 * 
 */
@Entity
@Table(name="tongtest")
@NamedQuery(name="Tongtest.findAll", query="SELECT t FROM Tongtest t")
public class Tongtest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="test_content", length=128)
	private String testContent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="test_time")
	private Date testTime;

	public Tongtest() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestContent() {
		return this.testContent;
	}

	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	public Date getTestTime() {
		return this.testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

}