package com.example;

import com.example.custom.tag.UserTag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCustomizeTag {

	@org.junit.jupiter.api.Test

	public void test(){
		//自定义标签解析
		ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/custom-tag.xml");
		UserTag userTag = (UserTag)ac.getBean("clark");
		System.out.println(userTag.getUsername());

	}
}
