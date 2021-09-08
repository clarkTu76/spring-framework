package com.example.custom.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ClarkController {

	@Autowired
	private ClarkService clarkService;

	public void say(){
		clarkService.say();
	}
}
