package com.example;

import org.springframework.context.ApplicationContext;

public class TestBfpp {

	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext xmlApplicationContext = new MyClassPathXmlApplicationContext("my-bfpp.xml");


	}
}
