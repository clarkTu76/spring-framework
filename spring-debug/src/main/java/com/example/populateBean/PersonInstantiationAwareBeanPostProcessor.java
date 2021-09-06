package com.example.populateBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class PersonInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	/**
	 * 如果不想让属性被后续的方法覆盖 返回 false
	 * @param bean the bean instance created, with properties not having been set yet
	 * @param beanName the name of the bean
	 * @return
	 * @throws BeansException
	 */
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
