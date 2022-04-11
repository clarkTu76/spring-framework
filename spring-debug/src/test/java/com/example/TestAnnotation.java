package com.example;

import org.springframework.context.ApplicationContext;

/**
 * BeanDefinition 和 BeanComponentDefinition
 * BeanDefinition 是设置在 beanFactory中的 BeanComponentDefinition设置在 CompositeComponentDefinition中
 *
 * 扫描到的注解的 BeanDefinition 是 ScannedGenericBeanDefinition
 * 自动注册的组件是 CompositeComponentDefinition  BeanComponentDefinition
 */
public class TestAnnotation {
	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new MyClassPathXmlApplicationContext("annotation.xml");


	}
}
