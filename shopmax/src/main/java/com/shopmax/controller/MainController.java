package com.shopmax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	//get 방식으로 들어오면 @GetMapping , post 방식으로 들어오면 @PostMapping
	@GetMapping(value = "/")  
	public String main() {
		return "main";
	}
	
}