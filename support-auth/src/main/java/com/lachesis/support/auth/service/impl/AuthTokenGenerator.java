package com.lachesis.support.auth.service.impl;

import java.util.Date;

import com.lachesis.support.auth.vo.AuthToken;

class AuthTokenGenerator {
	private String userid;
	private String password;
	private String terminalIpAddress;
	private String tokenValue;
	
	public AuthTokenGenerator(String userid, String password, String terminalIpAddress,String tokenValue){
		this.userid = userid;
		this.password = password;
		this.terminalIpAddress = terminalIpAddress;
		this.tokenValue = tokenValue;
	}
	
	public AuthToken generate(){
		AuthToken t = new AuthToken();
		t.setActive(true);
		t.setLastModified(new Date());
		t.setPassword(password);
		t.setTerminalIpAddress(terminalIpAddress);
		t.setUserid(userid);
		t.setTokenValue(tokenValue);
		
		return t;
	}
}
