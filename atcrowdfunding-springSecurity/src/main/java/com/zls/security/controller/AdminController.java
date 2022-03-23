package com.zls.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("/main.html")//GetMapping:RequestMapping + get请求方式的简写
	public String main(){
		return "main";
	}

	@GetMapping("/unauth.html")
	public String unauth(){
		return "unauth";
	}

}
