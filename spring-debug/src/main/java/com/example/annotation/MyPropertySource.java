package com.example.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:pp.properties")
public class MyPropertySource {

	@Value("#{name}")
	private String name;

}
