package com.jsm.forum.controller.personHomepageController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.plugin.activerecord.Page;
import com.jsm.forum.model.ForumPosts;

@Controller
@RequestMapping("/personHomepageController")
public class personHomepageController {

	/**
	 * 获取数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>(); 
		String userId = request.getParameter("userId");
		int  page = Integer.parseInt(request.getParameter("page"));
		String sql = " FROM forum_posts WHERE user_id = "+userId+" order by id desc";
		
		Page<ForumPosts> forumPosts = ForumPosts.dao.paginate(page, 5, "select * ", sql);
		Page<ForumPosts> forumPosts2 = ForumPosts.dao.paginate(page+1, 5, "select * ", sql);
		boolean flag = true;
		if(forumPosts2.getList().size()<=0) {
			flag = false;
		}
		
		String likeSql = "select praise,text from forum_posts where user_id = "+userId;
		List<ForumPosts>  fplike =  ForumPosts.dao.find(likeSql);
		int like = 0;
		int textlength= 0;
		for (ForumPosts forumPosts3 : fplike) {
			
			textlength+=forumPosts3.getText().length();
			
			if(forumPosts3.getPraise() != 0) {
				like += forumPosts3.getPraise();
			}
		}
		
			List<ForumPosts>  fp = forumPosts.getList();
			map.put("rows", fp);
			map.put("flag", true);
			map.put("flag2", flag);
			map.put("count", forumPosts.getTotalRow());
			map.put("like", like);
			map.put("textlength",textlength);
		
		return map;
	}
	
	/**
	 * 删除数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public boolean deleteOne(HttpServletRequest request) {
		String id = request.getParameter("id");
		if(id == null || id.equals("")) {
			return false;
		}
		
		ForumPosts f = new ForumPosts();
		f.setId(Integer.parseInt(id));
		
		boolean falg = f.delete();
		
		return falg;
	}
}
