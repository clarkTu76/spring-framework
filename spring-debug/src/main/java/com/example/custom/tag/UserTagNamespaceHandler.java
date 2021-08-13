package com.example.custom.tag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class UserTagNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("user",new UserTagBeanDefinitionParse());
	}
}
