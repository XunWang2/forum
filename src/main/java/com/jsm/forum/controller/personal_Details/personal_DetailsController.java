package com.jsm.forum.controller.personal_Details;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jsm.forum.commonUtil.getProperties;
import com.jsm.forum.model.BasicUser;

@Controller
@RequestMapping("/personal_DetailsController")
public class personal_DetailsController {

	@RequestMapping("/uploadImg")
	@ResponseBody
	public String uploadImg(@RequestParam("pic") MultipartFile pic, HttpServletRequest req, HttpSession session) {
		String imgName = pic.getOriginalFilename();
		int index = imgName.lastIndexOf(".");
		String lastName = imgName.substring(index, imgName.length());
		String uuid = UUID.randomUUID().toString();
		String newName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());

		// 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
		String u = getProperties.getPropertiesOne("imagePath") + "upload/headImage/" + newName + uuid + lastName;
		String address = "/headImage/" + newName + uuid + lastName;
        
		System.out.println("上传地址"+u);
		
		// 用户Id
		BasicUser user = (BasicUser) session.getAttribute("user");
		if (user == null) {

			return "请登陆";
		}

		File dir = new File(u);
		if (!dir.getParentFile().exists()) {
			dir.getParentFile().mkdirs();
		}
		try {
			pic.transferTo(dir);
		} catch (IllegalStateException e) {
			System.out.println("上传失败");
			e.printStackTrace();
			return "上传失败";
		} catch (IOException e) {
			System.out.println("上传失败");
			e.printStackTrace();
			return "上传失败";
		}

		if (user.getHeadImg() != null && !user.getHeadImg().equals("")) {
			String del = getProperties.getPropertiesOne("imagePath") + "upload/" + user.getHeadImg();
			File fil = new File(del);
			fil.delete();
		}
		user.setHeadImg(address);
		user.update();

		return address;
	}

	/**
	 * 修改个人信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/editInformation")
	@ResponseBody
	public Map<String, Object> editInformation(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String area = request.getParameter("area");

		BasicUser user = (BasicUser) session.getAttribute("user");
		user.setIntro(area);
		if (name != null && !name.equals("")) {
			user.setUsername(name);
		}

		if (sex != null && !sex.equals("")) {
			user.setSex(Integer.parseInt(sex));
		}

		if (area != null && !area.equals("")) {
			user.setIntro(area);
		}

		boolean flag = user.update();
		if (flag) {
			session.setAttribute("user", user);
			map.put("flag", true);
			map.put("msg", "修改成功");
		} else {
			map.put("flag", false);
			map.put("msg", "发生异常");
		}
		return map;
	}
}
