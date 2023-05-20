package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Cycle {


	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("cycle.xml");


	}
}
