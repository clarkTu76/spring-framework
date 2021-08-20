package com.example.annotation;

import com.example.User;
import org.springframework.context.annotation.Bean;

public class MyBean {

	@Bean
	public User getUser(){
		return new User(2L,"clark2");
	}
}
