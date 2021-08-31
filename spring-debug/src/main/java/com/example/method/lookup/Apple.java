package com.example.method.lookup;

public class Apple extends Fruit{

	//反例 一个单例bean 强引用一个原型bean 而不是lookup-method 哪怕引用的是一个原型bean 获取到的也是一同一个对象
	//private Banana banana;


	public Apple() {
		System.out.println("i got apple");
	}

	/*public Banana getBanana() {
		return banana;
	}

	public void setBanana(Banana banana) {
		this.banana = banana;
	}*/
}
