package com.buzone.vault.services.data.user;

public class NewPasswordUserRequest {
	private String key;
	
	private String password;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
