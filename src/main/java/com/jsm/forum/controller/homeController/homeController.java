package com.jsm.forum.controller.homeController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsm.forum.commonUtil.PubJfinal;
/**
 * 主页
 * @author jsm
 *
 */
@Controller
@RequestMapping("/homeController")
public class homeController {

	/**
	 * 获取主页数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		
	    int page = Integer.parseInt(request.getParameter("page"));
		String select = " select f.id,f.title,f.content,f.cover,f.user_id ,f.isShow,f.praise,f.trample,f.look_time,f.create_date,f.text,u.user_id ,u.username  ";
		String sql = " from forum_posts as f left join basic_user as u on f.user_id = u.user_id where f.isShow=1 order by f.id desc";
		Page<Record> records = Db.paginate(page, 10,select, sql);
		Map<String,Object> pageList = new HashMap<String,Object>();
		pageList = PubJfinal.paseMap(records);
		return pageList;
	}
	
	/**
	 * 获取评论总数
	 * @return
	 */
	@RequestMapping("/getCommentCount")
	@ResponseBody
	public long getCommentCount(@RequestParam("cid") int detailId) {
		// 查询有多少条评论
		String commentSql = "select count(*) as count  from comment where forum_posts_id=" + detailId;
		//查询子评论
		String CildcommentSql = "select count(*) as childCount  from child_comments where forum_posts_id=" + detailId;
		Record reord2 = Db.findFirst(CildcommentSql);
		Record reord = Db.findFirst(commentSql);
		long count = reord.get("count");
	    long count2 = reord2.get("childCount");
		return count+count2;
	}
	
}
