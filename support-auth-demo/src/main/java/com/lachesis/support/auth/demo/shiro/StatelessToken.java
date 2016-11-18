package com.lachesis.support.auth.demo.shiro;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;

public class StatelessToken implements AuthenticationToken {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5576063679753382282L;
	
	private String token;
	private String terminalIpAddress;
	private Map<String,?> params;
	
	public StatelessToken(String token, String terminalIpAddress, Map<String, ?> params) {
		super();
		this.token = token;
		this.terminalIpAddress = terminalIpAddress;
		this.params = params;
	}
	
	public String getToken() {
		return token;
	}

	public String getTerminalIpAddress() {
		return terminalIpAddress;
	}

	public Map<String, ?> getParams() {
		return params;
	}

	@Override
	public Object getPrincipal() {
		
		return token;
	}

	@Override
	public Object getCredentials() {
		return terminalIpAddress;
	}

}
