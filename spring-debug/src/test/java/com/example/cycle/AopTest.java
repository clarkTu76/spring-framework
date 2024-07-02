package com.example.cycle;

import com.example.aop.MyMethodService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: spring
 * @ClassName AopTest
 * @description:
 * @author: clark
 * @create: 2024-07-02 11:58
 * @Version 1.0
 **/
public class AopTest {

	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("aop_annotation.xml");
		MyMethodService myMethodService = ac.getBean(MyMethodService.class);
		myMethodService.doSomething();
	}


}
