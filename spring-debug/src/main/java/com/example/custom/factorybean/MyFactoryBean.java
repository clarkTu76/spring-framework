package com.example.custom.factorybean;

import com.example.User;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<User> {

	/**
	 * &MyFactoryBean 调用
	 * @return
	 * @throws Exception
	 */
	@Override
	public User getObject() throws Exception {
		return new User(1L,"clark");
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
