package com.jsm.forum.controller.addController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsm.forum.ImageUploadUtil.ImageUploadUtil;
import com.jsm.forum.commonUtil.getProperties;
import com.jsm.forum.model.BasicUser;
import com.jsm.forum.model.ForumPosts;

@Controller
@RequestMapping("/addController")
public class addController {

	@RequestMapping("/uploadImage")
	@ResponseBody
	public String uploadImage(@RequestParam("pic") String imageFile) {

		// 通过base64来转化图片
		imageFile = imageFile.replaceAll("data:image/jpeg;base64,", "");
		Base64 decoder = new Base64();
		// Base64解码
		byte[] imageByte = null;
		String path = null;
		try {
			imageByte = decoder.decode(imageFile);
			for (int i = 0; i < imageByte.length; ++i) {
				if (imageByte[i] < 0) {// 调整异常数据
					imageByte[i] += 256;
				}
			}

			// 生成jpeg图片
			String uuid = UUID.randomUUID().toString();
			String imageName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS-").format(new Date());
			 path = "/coverImage/"+ imageName + uuid + ".jpg";
			OutputStream out = new FileOutputStream(getProperties.getPropertiesOne("imagePath")+"upload"+ path);
			out.write(imageByte);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return path;
	}

	/**
	 * 编辑器上传图片
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadImg2")
	@ResponseBody
	public void uploadImg2(HttpServletRequest request, HttpServletResponse response) {

		String DirectoryName = getProperties.getPropertiesOne("imagePath")+"upload/";
		try {
			ImageUploadUtil.ckeditor(request, response, DirectoryName);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 添加帖子
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveContent")
	@ResponseBody
	public String saveContent(HttpServletRequest request) {
	
		String content = request.getParameter("Content");
		String title = request.getParameter("title");
		String coverPath = request.getParameter("coverPath");
		String isShow = request.getParameter("isShow");
		String text = request.getParameter("text");
		if(content == null || "".equals(content)) {
			return "内容不能为空";
		}
		if(title == null || "".equals(title)) {
			return "标题不能为空";
		}
		if(text == null || "".equals(text)) {
			return "内容不能为空";
		}
		
		ForumPosts posts = new ForumPosts();
		
		
		if(coverPath != null && (!coverPath.equals(""))) {
			posts.setCover(coverPath);
		}
		String time =   new SimpleDateFormat("yyy MM.dd HH:mm").format(new Date());
		
		posts.setCreateDate(time); //时间
		posts.setTitle(title); //标题
		posts.setContent(content); //内容
		posts.setIsShow(Integer.parseInt(isShow)); //是否公开
		HttpSession session = request.getSession(); 
		BasicUser user = (BasicUser) session.getAttribute("user");
		posts.setUserId(user.getUserId()); //用户Id
		posts.setText(text);
		boolean flag = posts.save();
		if(flag) {
			return "1";
		}else {
			return "保存失败";
		}
	}
	
	/**
	 * 根据Id查找
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public ForumPosts getOne(HttpServletRequest request){
		String id = request.getParameter("id");
		ForumPosts fp = ForumPosts.dao.findById(id);
		return fp;
	}
	
	/**
	 * 修改帖子
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(HttpServletRequest request) {
		String id = request.getParameter("id");
		String content = request.getParameter("Content");
		String title = request.getParameter("title");
		String coverPath = request.getParameter("coverPath");
		String isShow = request.getParameter("isShow");
		String text = request.getParameter("text");
		if(content == null || "".equals(content)) {
			return "内容不能为空";
		}
		if(title == null || "".equals(title)) {
			return "标题不能为空";
		}
		if(text == null || "".equals(text)) {
			return "内容不能为空";
		}
		
		ForumPosts posts = new ForumPosts();
		
		
		if(coverPath != null && (!coverPath.equals(""))) {
			posts.setCover(coverPath);
		}
		String time =   new SimpleDateFormat("yyy MM.dd HH:mm").format(new Date());
		posts.setId(Integer.parseInt(id));
		posts.setCreateDate(time); //时间
		posts.setTitle(title); //标题
		posts.setContent(content); //内容
		posts.setIsShow(Integer.parseInt(isShow)); //是否公开
		HttpSession session = request.getSession(); 
		BasicUser user = (BasicUser) session.getAttribute("user");
		posts.setUserId(user.getUserId()); //用户Id
		posts.setText(text);
		boolean flag = posts.update();
		if(flag) {
			return "1";
		}else {
			return "修改失败";
		}
	}
	

}
