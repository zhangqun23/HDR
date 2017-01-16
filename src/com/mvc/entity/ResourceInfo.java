package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the resource_info database table.
 * 
 */
@Entity
@Table(name="resource_info")
@NamedQuery(name="ResourceInfo.findAll", query="SELECT r FROM ResourceInfo r")
public class ResourceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=24)
	private String res_id;

	@Column(nullable=false, length=24)
	private String moduleRel_id;

	@Lob
	@Column(nullable=false)
	private String res_content;

	@Column(nullable=false, length=100)
	private String res_path;

	@Column(nullable=false, length=30)
	private String res_publicationer;

	@Column(nullable=false, length=30)
	private String res_publicationTime;

	@Column(nullable=false, length=30)
	private String res_title;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(length=30)
	private String reserve3;

	public ResourceInfo() {
	}

	public String getRes_id() {
		return this.res_id;
	}

	public void setRes_id(String res_id) {
		this.res_id = res_id;
	}

	public String getModuleRel_id() {
		return this.moduleRel_id;
	}

	public void setModuleRel_id(String moduleRel_id) {
		this.moduleRel_id = moduleRel_id;
	}

	public String getRes_content() {
		return this.res_content;
	}

	public void setRes_content(String res_content) {
		this.res_content = res_content;
	}

	public String getRes_path() {
		return this.res_path;
	}

	public void setRes_path(String res_path) {
		this.res_path = res_path;
	}

	public String getRes_publicationer() {
		return this.res_publicationer;
	}

	public void setRes_publicationer(String res_publicationer) {
		this.res_publicationer = res_publicationer;
	}

	public String getRes_publicationTime() {
		return this.res_publicationTime;
	}

	public void setRes_publicationTime(String res_publicationTime) {
		this.res_publicationTime = res_publicationTime;
	}

	public String getRes_title() {
		return this.res_title;
	}

	public void setRes_title(String res_title) {
		this.res_title = res_title;
	}

	public String getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return this.reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

}