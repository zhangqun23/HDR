package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 工程材料类别
 * 
 * @author wangrui
 * @date 2017年2月23日
 */
@Entity
@Table(name = "material_sort")
public class MaterialSort implements Serializable {
	private static final long serialVersionUID = 1076799945808298363L;

	private Integer sortId;
	private Byte isDeleted;
	private Integer parentId;
	private String sortName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@Column(name = "is_deleted", nullable = false)
	public Byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "parent_id", nullable = false)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "sort_name", nullable = false, length = 50)
	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}