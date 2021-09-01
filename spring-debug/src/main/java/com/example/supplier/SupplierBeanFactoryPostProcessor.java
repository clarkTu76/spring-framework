package com.example.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class SupplierBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		GenericBeanDefinition bd = (GenericBeanDefinition) beanFactory.getBeanDefinition("supplierUser");
		bd.setInstanceSupplier(CreateSupplierUser::createUser);
		bd.setBeanClass(SupplierUser.class);
	}
}
