package com.example;

import com.example.cycle.A;
import com.example.cycle.B;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCycle {

	/**
	 * 循环依赖
	 */
	@org.junit.jupiter.api.Test
	public void test(){

		ApplicationContext ac = new ClassPathXmlApplicationContext("cycle.xml");
		A a = ac.getBean(A.class);
		System.out.println(a.getB());

		B b = ac.getBean(B.class);
		System.out.println(b.getA());

	}
}
