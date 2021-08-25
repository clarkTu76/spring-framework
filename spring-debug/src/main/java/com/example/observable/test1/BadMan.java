package com.example.observable.test1;

import java.util.ArrayList;
import java.util.List;

public class BadMan implements Observable {

	private List<Observer> obs = new ArrayList();


	@Override
	public void addObserver() {

	}

	@Override
	public void removeObserver() {

	}

	@Override
	public void notifyObserver(String str) {

	}

	public void run(){
		System.out.println("开始逃跑");
		this.notifyObserver("追击");
	}
}
