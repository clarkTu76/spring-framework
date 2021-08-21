package com.example.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class MyMember {



	@Component
	@ComponentScan
	@Configuration
 	class InnerClass{

		@Component
		@ComponentScan
		@Configuration
		class InnerInnerClass{

		}
	}
}
