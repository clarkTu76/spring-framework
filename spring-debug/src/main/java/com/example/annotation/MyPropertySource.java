package com.example.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:pp.properties")
public class MyPropertySource {

	@Value("#{jdbc.url}")
	private String url;

}
