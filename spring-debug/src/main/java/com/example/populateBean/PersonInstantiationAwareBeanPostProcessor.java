package com.example.populateBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class PersonInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("PersonInstantiationAwareBeanPostProcessor 调用执行");
		Person person = null;
		if (bean instanceof Person) {
			person = (Person) bean;
			person.setName("zhangSan");
			return true;
		}
		return false;
	}
}
