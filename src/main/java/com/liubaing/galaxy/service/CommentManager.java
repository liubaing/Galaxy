package com.liubaing.galaxy.service;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.liubaing.galaxy.entity.Comment;
import com.liubaing.galaxy.entity.CommentsResult;
import com.liubaing.galaxy.entity.Platform;
import com.liubaing.galaxy.repository.jpa.CommentRepository;
import com.liubaing.galaxy.util.core.Const;
import com.liubaing.galaxy.util.core.JSONUtils;
import com.liubaing.galaxy.util.core.ObjectUtils;

@Service 
public class CommentManager {
	
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * 
	  * 方法描述：返回当前课程下JSON格式的分页评论
	  * @param courseID 课程ID
	  * @param pageID 页码
	  * @param Platform ANDROID或IOS
	  * @return <P>ANDROID:<code>{"total":12,"comments":[{"id":8,"sub_num":0,"text":"我的评论…","username":"用户姓名","date":"2012-03-07 12:19:00"}]}</code></p>
	  * 		<P>IOS:<code>[{"id":8,"sub_num":0,"text":"我的评论…","username":"用户姓名","date":"2012-03-07 12:19:00"}]</code></p>
	  * @author heshuai
	  * @version 2012-3-31 下午02:12:55
	 */
	public String get(int courseID, int pageID, Platform platform)
	{
		List<Comment> comments = this.get(courseID, pageID);
		switch (platform) {
		case ANDROID:
			long total = commentRepository.countByCourseid(courseID, Comment.DEFAULT_COMMENT_PARENT_ID);
			CommentsResult result = new CommentsResult(total, comments);
			return JSONUtils.toJson(result, JSONUtils.UNTIL_VERSION_10);
		default:
			return JSONUtils.toJson(comments, JSONUtils.UNTIL_VERSION_10);
		}
	}
	
	/**
	 * 
	  * 方法描述：返回当前课程下该用户JSON格式的分页评论
	  * @param courseID 课程ID
	  * @param userID 用户ID
	  * @param pageID 页码
	  * @return [{"id":10,"sub_num":2,"text":"我的评论…","username":"用户姓名","date":"2012-03-07 12:19:00"}]
	  * @author heshuai
	  * @version 2012-3-31 下午02:12:55
	 */
	public String get(int courseID, int userID, int pageID)
	{
		Pageable pageable = new PageRequest(--pageID, Const.RESULT_MAX);
		List<Comment> comments = commentRepository.findByCourseidAndUseridOrderByIdDesc(courseID, userID, pageable);
		List<Comment> temp = new LinkedList<Comment>();
		if (!ObjectUtils.isEmpty(comments)) {
			for(Comment comment : comments)
			{
				//评论为子评论的取父评论显示
				if (comment.getParentid()>0) {
					Comment comment_parent = commentRepository.findOne(comment.getParentid());
					//该父评论只显示一次
					if (!temp.contains(comment_parent)) {
						long count = this.getReplyNum(comment_parent.getId());
						comment_parent.setSub_num(count);
						temp.add(comment_parent);
					}
				}else {
					if (!temp.contains(comment)) {
						temp.add(comment);
					}
				}
			}
			//倒序排序
			Collections.sort(temp);
			return JSONUtils.toJson(temp,JSONUtils.UNTIL_VERSION_10);
		}
		return null;
	}
	
	/**
	 * 
	  * 方法描述：通过父评论ID获取响应子评论(无分页)
	  * @param commentID 父评论ID
	  * @return [{"id":43,"text":"hello","username":"cba001","date":"2012-05-11 13:59:52"}]
	  * @author heshuai
	  * @version 2012-5-10 上午11:56:47
	 */
	public String get(int commentID)
	{
		List<Comment> comments = commentRepository.findByParentidOrderByIdDesc(commentID);
		if (!ObjectUtils.isEmpty(comments)) {
			return JSONUtils.toJson(comments, JSONUtils.UNTIL_VERSION_11);
		}
		return null;
	}
	/**
	 * 
	  * 方法描述：获取分页评论
	  * @param courseID 课程ID 
	  * @param pageID 页码(从1开始)
	  * @author heshuai
	  * @version 2012-11-29 下午02:49:15
	 */
	private List<Comment> get(int courseID,int pageID)
	{
		Pageable pageable = new PageRequest(--pageID, Const.RESULT_MAX);
		List<Comment> comments = commentRepository.findByCourseidAndParentidOrderByIdDesc(courseID, Comment.DEFAULT_COMMENT_PARENT_ID, pageable);
		if (!ObjectUtils.isEmpty(comments)) {
			List<Comment> temp = new LinkedList<Comment>();
			for(Comment comment : comments)
			{
				//该评论下的回复数
				long count = this.getReplyNum(comment.getId());
				comment.setSub_num(count);
				temp.add(comment);
			}
			return temp;
		}
		return null;
	}
	
	/**
	 * 
	  * 方法描述：得到该课程下评论总数
	  * @param courseID  课程ID
	  * @return  {"count":"1"}
	  * @author heshuai
	  * @version 2012-5-11 下午12:00:37
	 */
	public String getCommentNum(int courseID)
	{
		long count = commentRepository.countByCourseid(courseID, Comment.DEFAULT_COMMENT_PARENT_ID);
		return  "{\"count\":"+count+"}";
	}
	
	/**
	 * 
	  * 方法描述：得到该评论下的回复总数
	  * @param commentID 父评论
	  * @author heshuai
	  * @version 2012-5-11 下午12:00:37
	 */
	private long getReplyNum(int commentID)
	{
		return commentRepository.countById(commentID);
	}
	
	/**
	 * 
	  * 方法描述：增加评论
	  * @param comment 评论JSON字符串
	  * @author heshuai
	  * @version 2012-3-13 下午06:08:00
	 */
	public String add(String comment,int courseID,int userID)
	{
		Comment temp = JSONUtils.fromJson(comment, Comment.class);
		if (temp != null) {
			temp.setCourseid(courseID);
			temp.setUserid(userID);
			temp.setCreateDate(new Date());
			commentRepository.save(temp);
			return JSONUtils.toJson(temp,JSONUtils.SINCE_VERSION_11);
		}
		return null;
	}
	
	/**
	 * 删除单条评论,注意：如果该评论为父评论，则连子评论一并删除
	 * @param commentID
	 */
	public void delete(int commentID)
	{
		commentRepository.deleteByIdOrParentid(commentID);
	}
	
}
