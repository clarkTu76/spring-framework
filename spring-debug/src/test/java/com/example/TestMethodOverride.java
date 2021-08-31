package com.example;

import com.example.method.lookup.Apple;
import com.example.method.lookup.Fruit;
import com.example.method.lookup.FruitPlate;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring中默认的对象都是单例的，spring会在一级缓存中持有对象，方便下次获取
 * 那么如果是原型作用域，会创建一个对象
 * 如果想在单例模式bean下引用一个原型模式下的bean 怎么办（原型对象是多例的 每次都是新的）
 *
 * 此时就要引用look-method 标签来解决此问题
 *
 * 通过拦截器的方式，每次需要的时候都会去创建需要的对象，而不会把原型对象缓存起来
 */
public class TestMethodOverride {

	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("methodOverride.xml");

		//反例 一个单例bean 强引用一个原型bean 而不是lookup-method 哪怕引用的是一个原型bean 获取到的也是一同一个对象
		/*Apple bean = ac.getBean(Apple.class);
		System.out.println(bean.getBanana());
		Apple bean2 = ac.getBean(Apple.class);
		System.out.println(bean2.getBanana());*/



		//获取的是不是同一个对象  如果apple 是单例的则是同一个对象  如果apple不是单列则不是
		FruitPlate fruitPlate1 = (FruitPlate) ac.getBean("fruitPlate1");
		fruitPlate1.getFruit();
		FruitPlate fruitPlate2 = (FruitPlate) ac.getBean("fruitPlate1");
		fruitPlate2.getFruit();


		/*//lookup-method 的作用
		FruitPlate fruitPlate1 = (FruitPlate) ac.getBean("fruitPlate1");
		// 进入intercept
		fruitPlate1.getFruit();

		FruitPlate fruitPlate2 = (FruitPlate) ac.getBean("fruitPlate2");
		fruitPlate2.getFruit();*/
	}
}
