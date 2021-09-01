package com.example;

import com.example.supplier.SupplierUser;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSupplier {

	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("supplier.xml");
		SupplierUser bean = ac.getBean(SupplierUser.class);
		System.out.println(bean.getUsername());

	}
}
