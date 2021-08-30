package com.example;

import org.springframework.context.ApplicationContext;

public class TestConverter {
	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new MyClassPathXmlApplicationContext("converter.xml");


	}
}
