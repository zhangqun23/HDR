package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the smit_host database table.
 * 
 */
@Entity
@Table(name="smit_host")
@NamedQuery(name="SmitHost.findAll", query="SELECT s FROM SmitHost s")
public class SmitHost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="host_id", unique=true, nullable=false)
	private int hostId;

	@Column(name="group_num", length=10)
	private String groupNum;

	@Column(name="host_ip", nullable=false, length=255)
	private String hostIp;

	@Column(name="host_name", nullable=false, length=255)
	private String hostName;

	@Column(name="host_port", nullable=false)
	private int hostPort;

	@Column(name="is_alert")
	private int isAlert;

	public SmitHost() {
	}

	public int getHostId() {
		return this.hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getGroupNum() {
		return this.groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getHostIp() {
		return this.hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getHostPort() {
		return this.hostPort;
	}

	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	public int getIsAlert() {
		return this.isAlert;
	}

	public void setIsAlert(int isAlert) {
		this.isAlert = isAlert;
	}

}