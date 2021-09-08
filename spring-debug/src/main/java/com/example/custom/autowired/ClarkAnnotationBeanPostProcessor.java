package com.example.custom.autowired;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;


/**
 * 模拟@Autowired 注解 抄一遍 AnnotationAwareBeanPostProcessor
 *@author  tul
 *@date 2021/9/8 9:22
 *
 */
@Component
public class ClarkAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware {
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

	}

	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

	}

	@Override
	public int getOrder() {
		return 200;
	}
}
