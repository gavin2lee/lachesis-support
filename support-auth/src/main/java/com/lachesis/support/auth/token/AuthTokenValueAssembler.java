package com.lachesis.support.auth.token;

import com.lachesis.support.auth.common.AuthConstants;

public class AuthTokenValueAssembler {
	private String terminalIpAdress;
	private String userid;
	public AuthTokenValueAssembler( String userid , String terminalIpAdress) {
		super();
		this.terminalIpAdress = terminalIpAdress;
		this.userid = userid;
	}
	
	public String buildTokenValue(){
		StringBuilder sb = new StringBuilder();
		sb.append(userid);
		sb.append(AuthConstants.TOKEN_PARTS_DELIMITER);
		sb.append(terminalIpAdress);
		sb.append(AuthConstants.TOKEN_PARTS_DELIMITER);
		sb.append(System.currentTimeMillis());
		
		return sb.toString();
	}
}
