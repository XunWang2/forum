package com.jsm.forum.controller.personHomepageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 个人主页映射
 * @author jsm
 *
 */
@Controller
@RequestMapping("/personHomepageMapping")
public class personHomepageMapping {

	
	//公开得别人可以查看
	@RequestMapping("/personHomepage")
	public String personHomepage() {
		
		return "/personalHomepage/personalHomepage";
	}
	
	//私有的只有自己可查看和修改or删除
	@RequestMapping("/PrivatePersonHomepage")
	public String PrivatePersonHomepage() {
		
		return "/personalHomepage/private_Personal_Homepage";
	}
}
