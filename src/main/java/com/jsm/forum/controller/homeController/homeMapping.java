package com.jsm.forum.controller.homeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 主页页面映射
 * @author redsoft
 */
@Controller
@RequestMapping("/homeMapping")
public class homeMapping {

	@RequestMapping("/homepage")
	public String homepage() {
		
		return "/homepage/homepage";
	}
	
	@RequestMapping("/header")
	public String header() {
		
		return "/header/header";
	}
	
}
