package com.example.custom.bdrpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class MyBeanDefinitionRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBeanDefinitionRegisterPostProcessor :: postProcessBeanFactory");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBeanDefinitionRegisterPostProcessor :: postProcessBeanDefinitionRegistry");

		BeanDefinitionBuilder builder =BeanDefinitionBuilder.rootBeanDefinition(Teacher.class);
		builder.addPropertyValue("name","zhangsan");

		registry.registerBeanDefinition("clarkName",builder.getBeanDefinition());

		//在调用时产生新的 beanDefinitionRegisterPostProcessor
		BeanDefinitionBuilder builderBDRPP =BeanDefinitionBuilder.rootBeanDefinition(CustomizedBeanDefinitionRegisterPostProcessor.class);
		registry.registerBeanDefinition("customizedBDRPP",builderBDRPP.getBeanDefinition());
	}
}
