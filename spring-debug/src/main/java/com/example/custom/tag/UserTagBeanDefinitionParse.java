package com.example.custom.tag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class UserTagBeanDefinitionParse extends AbstractSingleBeanDefinitionParser {

	/**
	 * 返回属性值所属的class类
	 * @param element the {@code Element} that is being parsed
	 * @return
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return UserTag.class;
	}


	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String username = element.getAttribute("username");
		String email = element.getAttribute("email");
		String password = element.getAttribute("password");

		if (StringUtils.hasText(username)){
			builder.addPropertyValue("username",username);
		}

		if (StringUtils.hasText(email)){
			builder.addPropertyValue("email",email);
		}

		if (element.hasAttribute("password")){
			builder.addPropertyValue("password",password);
		}

	}
}
