package com.example;

/**
 * @author : clark
 * @classname : User
 * @description : spring test
 * @date: 2020-09-19 10:39
 */

public class User {
	private String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				'}';
	}
}
