package com.example.method.lookup;

public class Apple extends Fruit{
	private Banana banana;


	public Apple() {
		System.out.println("i got apple");
	}

	public Banana getBanana() {
		return banana;
	}

	public void setBanana(Banana banana) {
		this.banana = banana;
	}
}
