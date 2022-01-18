package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	/**
	 * BeanDefinition 加载
	 * loadBeanDefinitions doLoadBeanDefinitions
	 * registerBeanDefinitions  doRegisterBeanDefinitions
	 * parseBeanDefinitions   parseDefaultElement
	 *
	 */
	@org.junit.jupiter.api.Test
	public void test(){
		//xml解析
		ApplicationContext xmlApplicationContext = new MyClassPathXmlApplicationContext("spring-bean.xml");

		User clark = (User)xmlApplicationContext.getBean("clark");
		System.out.println(clark);

	}

}
