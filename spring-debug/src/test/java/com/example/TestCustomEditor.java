package com.example;

import com.example.custom.editor.Customer;
import com.example.custom.tag.UserTag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCustomEditor {

	@org.junit.jupiter.api.Test
	public void test(){
		//自定义属性编辑器
		ApplicationContext ac = new ClassPathXmlApplicationContext("custom-editor.xml");
		Customer customer = ac.getBean(Customer.class);
		System.out.println(customer);

	}
}
