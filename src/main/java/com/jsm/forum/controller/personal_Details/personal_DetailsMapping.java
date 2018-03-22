package com.jsm.forum.controller.personal_Details;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/personal_DetailsMapping")
@Controller
public class personal_DetailsMapping {

	@RequestMapping("/personal_Details")
	public String personal_Details() {
		
		return "/personalDetails/personalDetails";
	}
}
