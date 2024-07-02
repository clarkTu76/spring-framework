package com.example.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: spring
 * @ClassName MethodAspect
 * @description:
 * @author: clark
 * @create: 2024-07-02 11:47
 * @Version 1.0
 **/
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class MethodAspect {


	@Before("execution(* com.example.aop.MyMethodServiceImpl..*(..)))")
	public void before() {
		System.out.println("before method");
	}


	@After("execution(* com.example.aop.MyMethodServiceImpl..*(..)))")
	public void after() {
		System.out.println("after method");
	}


}
