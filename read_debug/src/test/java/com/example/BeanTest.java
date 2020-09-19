package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author : clark
 * @classname : BeanTest
 * @description :
 * @date: 2020-09-19 10:53
 */

public class BeanTest {
	@Test
	public void test(){
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("xmlBeanFactory.xml"));
		Object user = beanFactory.getBean("user");
		System.out.println(user);
	}

	public static void main(String[] args) {
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("xmlBeanFactory.xml"));
		Object user = beanFactory.getBean("user");
		System.out.println(user);
	}

}
