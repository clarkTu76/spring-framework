package com.example;

import org.springframework.context.ApplicationContext;

public class TestFactoryBean {

	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new MyClassPathXmlApplicationContext("factoryBean.xml");


	}

}
