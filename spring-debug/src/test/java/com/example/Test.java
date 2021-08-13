package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	@org.junit.jupiter.api.Test
	public void test(){
		//xml解析
		//ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("spring-bean.xml");
		//自定义标签解析
		ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("custom-tag.xml");
		//ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");

		User clark = (User)xmlApplicationContext.getBean("clark");
		System.out.println(clark);

	}

}
