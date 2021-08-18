package com.example.custom.bdrpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

public class CustomizedBeanDefinitionRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("CustomizedBeanDefinitionRegisterPostProcessor :: postProcessBeanFactory");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("CustomizedBeanDefinitionRegisterPostProcessor :: postProcessBeanDefinitionRegistry");
	}


	@Override
	public int getOrder() {
		return 0;
	}
}
