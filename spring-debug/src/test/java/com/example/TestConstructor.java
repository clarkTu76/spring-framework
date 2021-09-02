package com.example;

import com.example.constructor.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConstructor {

	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("constructor.xml");
		Person bean = ac.getBean(Person.class);

	}
}
