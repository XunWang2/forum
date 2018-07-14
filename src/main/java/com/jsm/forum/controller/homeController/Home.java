package com.jsm.forum.controller.homeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {

	@RequestMapping("/")
	public String homepage() {
		return "/homepage/homepage";
	}
}
