package com.example.custom.bdrpp;

public class Teacher{
	private String name;

	public Teacher() {
		System.out.println("Teacher 无参构造器");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher{" +
				"name='" + name + '\'' +
				'}';
	}
}
