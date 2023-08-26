package com.hackathon.authenticationservice.model;


public class LoginRequest {
	private String userName;
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(String name, String password) {
		super();
		this.userName = name;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
