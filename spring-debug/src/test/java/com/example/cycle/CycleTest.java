package com.example.cycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CycleTest {


	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("cycle.xml");


	}
}
