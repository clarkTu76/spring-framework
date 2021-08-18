package com.example;

import org.springframework.context.ApplicationContext;

public class TestAnnotation {
	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new MyClassPathXmlApplicationContext("annotation.xml");


	}
}
