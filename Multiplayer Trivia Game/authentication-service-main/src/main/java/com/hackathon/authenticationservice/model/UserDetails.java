package com.hackathon.authenticationservice.model;

public class UserDetails {

	private String useName;
	private String password;
	private Role role;

	public UserDetails() {
		super();
	}

	public UserDetails(String name, String password, Role role) {
		super();
		this.useName = name;
		this.password = password;
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return useName;
	}

	public void setUserName(String name) {
		this.useName = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
