package com.example;

import com.example.aop.service.MyCalculator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {

	@org.junit.jupiter.api.Test
	public void test() throws NoSuchMethodException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
		MyCalculator bean = ac.getBean(MyCalculator.class);
		bean.add(1,1);

	}

}
