package com.example.customtag;

import com.example.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author : clark
 * @classname : UserTagParser
 * @description : 自定义标签解析器
 * @date: 2020-11-22 20:08
 */

public class UserTagParser extends AbstractSingleBeanDefinitionParser {


	@Override
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}


	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String name = element.getAttribute("name");
		String email = element.getAttribute("email");
		String age = element.getAttribute("age");
		String id = element.getAttribute("id");

		if (StringUtils.hasText(name)){
			builder.addPropertyValue("name",name);
		}

		if (StringUtils.hasText(email)){
			builder.addPropertyValue("email",email);
		}

		if (StringUtils.hasText(age)){
			builder.addPropertyValue("age",age);
		}

		if (StringUtils.hasText(id)){
			builder.addPropertyValue("id",age);
		}

	}
}
