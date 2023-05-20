package com.example.cycle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {

	@Autowired
	private B b;

	@Override
	public String toString() {
		return "A{" +
				"b=" + b +
				'}';
	}
}
