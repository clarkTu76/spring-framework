package com.example.cycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 循环依赖 A-> B ->A
 *
 * doGetBean getSingleton1 getSingleton2(createBean -> addSingleton)
 *
 * doCreateBean -> populateBean
 *
 * 实例化A 然后判断是否需要提前暴露 如果需要则把获取提前暴露的lambda(getEarlyBeanReference)放到三级缓存里面(addSingletonFactory)
 *populateBean A -> applyPropertyValues ->resolveValueIfNecessary -> resolveReference -> getBean
 *
 * 实例化B 把B的半成品引用获取Lambda放到三级缓存里面
 * populateBean B -> applyPropertyValues ->resolveValueIfNecessary -> resolveReference -> getBean -> getSingleton(true)
 *
 * getSingleton：A begin (DefaultSingletonBeanRegistry.java)
 * 先去一级缓存里面获取A  若没有从二级里面查 如没有加锁 再重复查一二级 没有就查三级 三级里面有 就调用 lambda.getObject()实际就是 getEarlyBeanReference
 * getEarlyBeanReference里获取到A的代理半成品 放入二级缓存  从三级缓存中移除
 * getSingleton：A end
 *
 * 递归返回 populateBean B执行完
 * getSingleton(false) B 返回暴露的B
 * 返回 B的 getSingleton -> addSingleton 把B放入一级缓存(此时B还在三级缓存里) 删除三级缓存
 *
 *
 * 递归返回 populateBean A执行完
 * getSingleton(false) A  返回暴露的A
 *
 * 返回 A的 getSingleton -> addSingleton 把A放入一级缓存 删除二级缓存
 *
 * A->B->A的循环里 AB都需要提前暴露  但是B直接从三级到一级  A从三级到二级再到一级
 */
public class CycleTest {


	@org.junit.jupiter.api.Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("cycle.xml");
		ac.getBean("a");

	}
}
