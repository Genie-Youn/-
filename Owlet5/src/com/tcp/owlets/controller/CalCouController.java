package com.tcp.owlets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/calcou")
public class CalCouController {
	
	@RequestMapping("/main")
	public String getCalendarPage(){
		return "calcoumain";
	}
}
