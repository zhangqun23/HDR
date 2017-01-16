package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the service_printer database table.
 * 
 */
@Entity
@Table(name="service_printer")
@NamedQuery(name="ServicePrinter.findAll", query="SELECT s FROM ServicePrinter s")
public class ServicePrinter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="printer_id", unique=true, nullable=false)
	private int printerId;

	@Column(name="deleted_flag", nullable=false)
	private int deletedFlag;

	@Column(name="printer_name", nullable=false, length=255)
	private String printerName;

	@Column(name="printer_remark", length=255)
	private String printerRemark;

	@Column(name="printer_type", nullable=false)
	private int printerType;

	@Column(name="servict_type", nullable=false, length=10)
	private String servictType;

	public ServicePrinter() {
	}

	public int getPrinterId() {
		return this.printerId;
	}

	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

	public int getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(int deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getPrinterName() {
		return this.printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getPrinterRemark() {
		return this.printerRemark;
	}

	public void setPrinterRemark(String printerRemark) {
		this.printerRemark = printerRemark;
	}

	public int getPrinterType() {
		return this.printerType;
	}

	public void setPrinterType(int printerType) {
		this.printerType = printerType;
	}

	public String getServictType() {
		return this.servictType;
	}

	public void setServictType(String servictType) {
		this.servictType = servictType;
	}

}