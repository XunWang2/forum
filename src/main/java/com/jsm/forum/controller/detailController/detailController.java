package com.jsm.forum.controller.detailController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsm.forum.Service.detailService;
import com.jsm.forum.commonUtil.PubJfinal;
import com.jsm.forum.model.BasicUser;
import com.jsm.forum.model.ChildComments;
import com.jsm.forum.model.Comment;
import com.jsm.forum.model.ForumPosts;
import com.jsm.forum.model.ILikeTheArticle;
import com.jsm.forum.model.ILikeTheComment;

@RequestMapping("/detailController")
@Controller
public class detailController {

	@Autowired
	detailService detailservice;

	
	
	
	/**
	 * 详情页
	 * 
	 * @param detailId
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public Map<String, Object> detail(@RequestParam("detailId") String detailId, HttpSession session,@RequestParam("page") int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapList = new HashMap<String, Object>();
		if (detailId == null || "".equals(detailId)) {
			map.put("ErrorMsg", "detailId为空");
			map.put("success", "0");
		}
		BasicUser user = (BasicUser) session.getAttribute("user");
		if (user != null) {

		}
		String sql = "select * from forum_posts where id=" + detailId;
		ForumPosts posts = null;
		posts = ForumPosts.dao.findFirst(sql);
		if (posts != null) {

			int userId = posts.getUserId();
			String CountSql = "select text,praise from forum_posts where user_id = " + userId;
			Record  BU = Db.findById("basic_user", "user_id", userId);
			List<ForumPosts> CountText = new ArrayList<ForumPosts>();
			CountText = ForumPosts.dao.find(CountSql);
			int textCount = 0;
			int like = 0;
			for (ForumPosts forumPosts : CountText) {
				textCount += forumPosts.getText().length();
				like += forumPosts.getPraise();
				
			}

			// 查询有多少条评论
			String commentSql = "select count(*) as count  from comment where forum_posts_id=" + detailId;
			//查询子评论
			String CildcommentSql = "select count(*) as childCount  from child_comments where forum_posts_id=" + detailId;
			Record reord2 = Db.findFirst(CildcommentSql);
			Record reord = Db.findFirst(commentSql);
			String commentSql1 = "SELECT COUNT(DISTINCT c.id ),  c.id as commentId,c.user_id,c.forum_posts_id,c.content,c.create_Date,c.like,f.head_Img,f.user_id as userid,f.username,ff.id,i.user_id as likeUserId,i.comment_id  ";
			String commentSql2 = "  FROM COMMENT AS c LEFT JOIN basic_user AS f  ON c.user_id = f.user_id  left join forum_posts as ff on c.forum_posts_id = ff.id left join i_like_the_comment as i on i.comment_id = c.id    where ff.id="
					+ detailId + " GROUP BY c.id  order by  c.id desc ";
			String SerchLikeRecordSql = "SELECT f.id ,c.id AS cid,c.forum_posts_id, i.comment_id,i.user_id FROM forum_posts AS f \r\n"
					+ "LEFT JOIN COMMENT AS c ON f.id = c.forum_posts_id LEFT JOIN i_like_the_comment AS i ON i.comment_id = c.id   WHERE f.id = "
					+ detailId;

			List<Record> SerChLikeRecord = Db.find(SerchLikeRecordSql);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < SerChLikeRecord.size(); i++) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("commentids", SerChLikeRecord.get(i).get("comment_id"));
				map2.put("userids", SerChLikeRecord.get(i).get("user_id"));
				list.add(map2);
			}
			map.put("map2", list);
			
			
			
			Page<Record> commentRecord = Db.paginate(page, 5, commentSql1, commentSql2);
			Page<Record> commentRecord2 = Db.paginate(page+1, 5, commentSql1, commentSql2);
			boolean ff = false;
			if(commentRecord2.getList().size()==0) {
				ff =true;
			}
			
			mapList = PubJfinal.paseMap(commentRecord);
			map.put("CommentList", mapList);
			long count = reord.get("count");
		    long count2 = reord2.get("childCount");
		    map.put("isHave", ff);
			map.put("commentComment", count + count2);
			map.put("like", like);
			map.put("textCount", textCount);
			map.put("one", posts);
			map.put("intro", BU.get("intro"));
			map.put("success", "1");
		} else {
			map.put("ErrorMsg", "发生异常错误");
			map.put("success", "0");
		}

		return map;
	}

	/**
	 * 点赞
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/like")
	@ResponseBody
	public String like(HttpServletRequest request) {
		// 文章Id
		String articleId = request.getParameter("articleId");
		// 用户Id
		HttpSession session = request.getSession();
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		String result = "";
		try {
			result = detailservice.likeImpl(userId, articleId);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * 初始化喜欢按钮
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/initLike")
	@ResponseBody
	public String initLike(HttpServletRequest request) {
		// 文章Id
		String articleId = request.getParameter("articleId");
		// 用户Id
		HttpSession session = request.getSession();
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		String sql = "select * from i_like_the_article where article_id=" + articleId + " and user_id=" + userId;
		List<ILikeTheArticle> list = ILikeTheArticle.dao.find(sql);
		if (list.size() > 0 && list != null) {

			return "yes";
		}
		return "no";

	}

	/**
	 * 添加评论
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户Id
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		// 文章Id
		String articleId = request.getParameter("articleId");
		// 内容
		String content = request.getParameter("content");
		// 时间
		String createDate = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());
		Comment ct = new Comment();
		ct.setUserId(userId);
		ct.setForumPostsId(Integer.parseInt(articleId));
		ct.setContent(content);
		ct.setCreateDate(createDate);
		boolean flag = ct.save();
		if (flag) {

			String sql = "select count(id) as count from comment where forum_posts_id=" + articleId;
			Record record = Db.findFirst(sql);
			map.put("username", user.getUsername());
			map.put("floor", record.get("count") + "楼 · " + createDate);
			map.put("msg", "回复成功");
			map.put("ct", ct);
		} else {
			map.put("msg", "回复失败");
		}
		return map;
	}

	/**
	 * 评论点赞
	 * 
	 * @param request
	 * @param session
	 */
	@RequestMapping("/commentLike")
	@ResponseBody
	public void commentLike(HttpServletRequest request, HttpSession session) {

		// 用户Id
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();

		String mark = request.getParameter("mark");
		String commentId = request.getParameter("commentId");

		try {
			detailservice.commentLike(commentId, mark, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断用户是否已经点赞
	@RequestMapping("/judgeIsLike")
	@ResponseBody
	public boolean judgeIsLike(HttpServletRequest request, HttpSession session) {
		// 评论得Id
		String commentId = request.getParameter("commentId");
		// 用户Id
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		String sql = "select * from i_like_the_comment WHERE user_id=" + userId + " and comment_id=" + commentId;

		ILikeTheComment ct = ILikeTheComment.dao.findFirst(sql);
		if (ct == null) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 添加子评论
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/childComment")
	@ResponseBody
	public Map<String, Object> childComment(HttpServletRequest request, HttpSession session) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 用户Id
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		// 文章Id
		String articleId = request.getParameter("articleId");
		// 内容
		String content = request.getParameter("content");
		// 父评论id
		String parentCommentId = request.getParameter("parentCommentId");
		if(userId == null && articleId == null && "".equals(articleId) && content == null && "".equals(content) && parentCommentId == null && "".equals(parentCommentId)) {
			map.put("flag", false);
			map.put("msg", "有信息为空，请重新输入");
			return map;
		}
		// 时间
		String createDate = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());
        ChildComments cc = new ChildComments();
        cc.setUserId(userId);
        cc.setForumPostsId(Integer.parseInt(articleId));
        cc.setContent(content);
        cc.setParentCommentId(Integer.parseInt(parentCommentId));
        cc.setCreateDate(createDate);
        boolean flag = cc.save();
        if(flag) {
            map.put("id", cc.getId());
        	map.put("username", user.getUsername());
        	map.put("flag", true);
        	map.put("msg", "回复成功");
        }else {
        	map.put("flag", false);
			map.put("msg", "回复失败，请重试！");
        }
		return map;
	}
	
	/**
	 * 用户自己删除父评论
	 * @param request
	 * @return
	 */
	@RequestMapping("/delParentComment")
	@ResponseBody
	public boolean delParentComment(HttpServletRequest request , HttpSession session ) {
		String id = request.getParameter("id");
		// 用户Id
		BasicUser user = (BasicUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		boolean flag = false;
		try {
		 flag = detailservice.delComment(id, userId);
		return flag;
		}catch (Exception e) {
        e.toString();
		}
		return flag;
	
	}
	
	
	/**
	 * 用户自己删除子评论
	 * @param request
	 * @return
	 */
	@RequestMapping("/delChildComment")
	@ResponseBody
	public boolean delChildComment(HttpServletRequest request , HttpSession session ) {
		String id = request.getParameter("id");
		boolean flag = false;
		flag = Db.deleteById("child_comments", id);
		return flag;
	
	}
	
	
	/**
	 * 访问次数
	 * @param request
	 */
	@RequestMapping("/addLook")
	@ResponseBody
	public void addLook(HttpServletRequest request) {
		
		String id = request.getParameter("detailId");
		ForumPosts fp = ForumPosts.dao.findById(id);
		int lookTime = fp.getLookTime();
		lookTime++;
		fp.setLookTime(lookTime);
		fp.update();
	}
	
	/**
	 * 根据父评论id查找对应的子评论
	 * @return
	 */
	@RequestMapping("/getChildComment")
	@ResponseBody
	public Map<String,Object> getChildComment(@RequestParam("id") String id,@RequestParam("page") int page){
	    String sql1 = "select c.id as cid,c.user_id,c.content,c.create_date,parent_comment_id,u.user_id  as uid,u.username";
		String sql = "   from child_comments as c left join basic_user as u on c.user_id = u.user_id where parent_comment_id ="+id;
		Page<Record> list = Db.paginate(page, 2, sql1, sql);
		Map<String,Object> map = PubJfinal.paseMap(list);
		
		return map;
	}
	
	
	

}
