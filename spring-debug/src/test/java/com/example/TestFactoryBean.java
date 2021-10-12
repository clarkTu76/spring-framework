package com.example;

import org.springframework.context.ApplicationContext;

public class TestFactoryBean {

	/**
	 * 实例化的第一步 是否 factoryBean
	 * 三级缓存检查 bean是否正在创建中
	 * 第二步 lookUp-method、replace-method
	 * 第三步 instantiationAwareBPP
	 * 第四步 BeanWrapper
	 * 填充属性 supplier
	 * 执行applyBeanPostProcessorsBeforeInitialization @PostConstruct
	 * bean标签的init-method   InitializingBean 中的 afterPropertiesSet
	 * applyBeanPostProcessorsAfterInitialization
	 * close
	 */
	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new MyClassPathXmlApplicationContext("factoryBean.xml");


	}

}
