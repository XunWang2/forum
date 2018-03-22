package com.jsm.forum.controller.detailController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 详情页面映射
 * @author qq165
 *
 */
@RequestMapping("/detailMapping")
@Controller
public class detailMapping {

	//旧详情页面
	@RequestMapping("/detail")
	public String detail() {
		
		return "/detail/detail";
	}
	
	//新详情页面
	@RequestMapping("/detail2")
	public String detail2() {
		
		return "/detail/detail2";
	}
	
}
