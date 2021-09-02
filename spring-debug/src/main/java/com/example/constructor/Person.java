package com.example.constructor;


import org.springframework.stereotype.Component;

/**
 * 构造方法的选择测试
 */
@Component
public class Person {

	private Integer id;
	private String name;

	public Person() {
		System.out.println("Person() 构造方法");
	}

	public Person(Integer id) {
		System.out.println("Person(Integer id) 构造方法");
		this.id = id;
	}

	public Person(String name) {
		System.out.println("Person(String name) 构造方法");
		this.name = name;
	}

	public Person(Integer id, String name) {
		System.out.println("Person(Integer id, String name) 构造方法");
		this.id = id;
		this.name = name;
	}

	public Person(String name,Integer id) {
		System.out.println("Person(String name,Integer id) 构造方法");
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
