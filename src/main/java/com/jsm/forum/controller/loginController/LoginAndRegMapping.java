package com.jsm.forum.controller.loginController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录和注册页面映射
 * @author jsm
 *
 */
@Controller
@RequestMapping("/LoginAndRegMapping")
public class LoginAndRegMapping {

	 /**
	  * 登录页面
	  * @return
	  */
	 @GetMapping("/login")
	 public String login() {
		 return "/LoginAndReg/login";
	 }
	 
	 /**
	  * 注册页面
	  * @return
	  */
	 @GetMapping("/reg")
	 public String reg() {
		 return "/LoginAndReg/signup";
	 }
}
