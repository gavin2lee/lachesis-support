package com.lachesis.support.auth.demo.vo;

public class SimpleUserVo {
	private String userId;
	private String username;
	
	public SimpleUserVo() {
		super();
	}
	
	public SimpleUserVo(String userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}
