	package com.jsm.forum.controller.loginController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hazelcast.util.MD5Util;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jsm.forum.model.BasicUser;

/**
 * 登录和注册
 * 
 * @author jsm
 *
 */
@Controller
@RequestMapping("/LoginAndRegController")
public class LoginAndRegController {

	/**
	 * 注册
	 * @param request
	 * @return
	 */
	@RequestMapping("/reg")
	@ResponseBody
	public String reg(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if ("".equals(username) && username == null) {
			return "用户名不能空";
		}
		String checkSql = "select * from basic_user where username='" + username + "'";
		List<Record> records = Db.find(checkSql);
		if (records.size() > 0) {
			return "用户名已被注册，请重新输入";
		}
		
		if ("".equals(password) && password == null) {
			return "密码不能空";
		}

		BasicUser user = new BasicUser();
		user.setUsername(username);
		user.setPassword(MD5Util.toMD5String(password));
		boolean flag = user.save();
		if (flag) {
			return "1";
		}
		return "注册失败，请重试";
	}

	/**
	 * 查询用户名是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/CheckUserName")
	@ResponseBody
	public String CheckUserName(HttpServletRequest request) {
		String username = request.getParameter("username");
		if (username == null && "".equals(username)) {
			return "用户名不能空";
		}
		String checkSql = "select * from basic_user where username='" + username + "'";
		List<Record> records = Db.find(checkSql);
		if (records.size() > 0) {
			return "用户名已被注册，请重新输入";
		}
		return "";
	}
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(@Valid BasicUser user,HttpServletRequest request) {
		if(user != null) {
			String username = user.getUsername();
			String password = user.getPassword();
			String sql = "select * from basic_user where username='"+username+"' and password='"+MD5Util.toMD5String(password)+"'";
			List<BasicUser> users =  BasicUser.dao.find(sql);
			if(users != null && users.size() > 0) {
				HttpSession session = request.getSession();
				session.setAttribute("user", users.get(0));
				return "1";
			}else {
				return "2";
			}
		}
		return "0";
	}
	
	// 注销登陆
	@RequestMapping("/cancel")
	public String cancel(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		
		 return "/LoginAndReg/login";
	}

}
