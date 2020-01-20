package com.openpayd.currencyconversion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ApplicationController {

	@GetMapping(value = "hello", produces = "application/json")
	@ResponseBody
	public String getHello() {
		return "Hello";
	}

}
