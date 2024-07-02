package com.example.aop;

import org.springframework.stereotype.Service;

/**
 * @program: spring
 * @ClassName MyMethod
 * @description:
 * @author: shuying
 * @create: 2024-07-02 11:48
 * @Version 1.0
 **/
@Service
public class MyMethodServiceImpl implements MyMethodService {


	public String doSomething() {
		System.out.println("doSomething");
		return "Hello World";
	}


}
