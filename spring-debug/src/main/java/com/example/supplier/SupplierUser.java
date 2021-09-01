package com.example.supplier;

public class SupplierUser {

	private String username;

	public SupplierUser() {
	}

	public SupplierUser(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "SupplierUser{" +
				"username='" + username + '\'' +
				'}';
	}
}
