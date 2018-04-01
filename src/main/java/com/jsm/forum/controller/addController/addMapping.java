package com.jsm.forum.controller.addController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 添加页面映射
 * @author qq165
 *
 */
@Controller
@RequestMapping("/addMapping")
public class addMapping {

	/**
	 * 编辑页
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "/add/add2";
	}
	
	/**
	 * 编辑页
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit() {
		return "/add/eidt";
	}
	
	@RequestMapping("/upload")
	public String upload() {
		return "/add/uploadImage";
	}
}
