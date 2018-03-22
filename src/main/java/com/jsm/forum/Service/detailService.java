package com.jsm.forum.Service;


import static org.mockito.Matchers.anyDouble;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jsm.forum.model.Comment;
import com.jsm.forum.model.ForumPosts;
import com.jsm.forum.model.ILikeTheArticle;
import com.jsm.forum.model.ILikeTheComment;

@Service
public class detailService {
	
	
	@Transactional 
	public String likeImpl(Integer userId,String articleId) {
			
		    try {
		        String sql = "select * from i_like_the_article where article_id="+articleId+" and user_id="+userId;
		        String sql2 = "select id,praise from forum_posts where id="+articleId;
		        ForumPosts forumPosts = ForumPosts.dao.findFirst(sql2);
		        List<ILikeTheArticle> list = ILikeTheArticle.dao.find(sql);
				if(list.size()>0 && list!=null) {
					Object id = list.get(0).get("id");
					ILikeTheArticle.dao.deleteById(id);
					Integer praise = forumPosts.getPraise();
					praise--;
					forumPosts.setPraise(praise);
					forumPosts.update();
					return "取消喜欢";
				}
		    	
		    	ILikeTheArticle iLikeTheArticle = new ILikeTheArticle();
				iLikeTheArticle.setUserId(userId);
				iLikeTheArticle.setArticleId(Integer.parseInt(articleId));
				iLikeTheArticle.save();
				
				
				Integer praise = forumPosts.getPraise();
				praise++;
				forumPosts.setPraise(praise);
				forumPosts.update();
				return "增加喜欢";
			} catch (Exception e) {
				throw new RuntimeException("点赞发生异常了..");
			}
	}
	
	@Transactional 
    public void commentLike(String commentId,String mark,Integer userId) {
		try {
    	String sql = "select * from comment where id = "+commentId;
		Comment ct = Comment.dao.findFirst(sql);
		
		String sql2 = "select * from i_like_the_comment where user_id="+userId+" and comment_id="+commentId;
		List<ILikeTheComment> iLikeTheComment2 = ILikeTheComment.dao.find(sql2);
	/*	if(iLikeTheComment2.size()>0 && iLikeTheComment2 != null) {
			
			return;
		}*/
		
		String likeSql = "select * from i_like_the_comment where user_id="+userId+" and comment_id="+commentId;
	//	ILikeTheComment iLikeTheComment = ILikeTheComment.dao.findFirst(likeSql);
		ILikeTheComment iLikeTheComment = new ILikeTheComment();
			iLikeTheComment.setUserId(userId);
			iLikeTheComment.setCommentId(Integer.parseInt(commentId));
	
		
		
		int like = ct.getLike();
		
		if(mark.equals("1")) {
			like++;
			iLikeTheComment.save();
		}else {
			
			if(iLikeTheComment2.size()>0 && iLikeTheComment2 != null) {
				iLikeTheComment2.get(0).delete();
			}
			like--;
		}
		ct.setLike(like);
		ct.update();
	} catch (Exception e) {
		throw new RuntimeException("评论点赞发生异常了..");
	}
    }
	
	@Transactional
	public boolean delComment(String id,Integer userid) {
		boolean flag = false;
		try {
		flag = Db.deleteById("i_like_the_comment", "comment_id", id);
		flag = Db.deleteById("comment", id);
		return flag;
		}catch (Exception e) {
			throw new RuntimeException("删除评论发生异常了..");
		}
		
	}
}
