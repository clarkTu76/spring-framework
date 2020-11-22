package com.example.customtag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author : clark
 * @classname : UserTagHandler
 * @description : 自定义标签处理器
 * @date: 2020-11-22 20:13
 */

public class UserTagHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("user",new UserTagParser());
	}
}
