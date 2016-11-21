package com.lachesis.support.auth.vo;

import java.io.Serializable;
import java.util.Date;

public class AuthToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4247871662305154446L;
	private Long oid;
	private String tokenValue;
	private String terminalIpAddress;
	private boolean active;
	private Date lastModified;
	private String userid;
	private String password;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getTokenValue() {
		return tokenValue;
	}
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	public String getTerminalIpAddress() {
		return terminalIpAddress;
	}
	public void setTerminalIpAddress(String terminalIpAddress) {
		this.terminalIpAddress = terminalIpAddress;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
