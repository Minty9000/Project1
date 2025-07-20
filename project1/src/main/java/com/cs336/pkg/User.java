package com.cs336.pkg;

public class User {
	public int user_id;
	public String username;
	public String password;
	public String email;
	public String phone;
	public String role;
	
	public User (int user_id, String username, String password, String email, String phone, String role) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}
	
}
