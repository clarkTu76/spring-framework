package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPopulate {

	@org.junit.jupiter.api.Test
	public void test(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("populateBean.xml");
		ac.close();

	}

}
