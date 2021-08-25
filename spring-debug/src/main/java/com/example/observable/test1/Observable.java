package com.example.observable.test1;

public interface Observable {

	void addObserver();
	void removeObserver();
	void notifyObserver(String str);
}
