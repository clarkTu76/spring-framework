package com.example;

import com.example.resolveBeforeInstantiation.BeforeInstantiation;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBeforeInstantiation {

	@Test
	public void test(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beforeInstantiation.xml");
		BeforeInstantiation bean = ac.getBean(BeforeInstantiation.class);
		bean.doSomething();

	}
}
