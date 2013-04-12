package com.liubaing.shiny_liubaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liubaing.shiny_liubaing.entity.Platform;
import com.liubaing.shiny_liubaing.service.CommentManager;

@Controller
@RequestMapping("/course/comments")
public class CommentController {
    
	@Autowired
	private CommentManager commentManager;
	/**
	 * 
	 * 方法描述：该课程下所有评论(有分页)
	 * @author: heshuai
	 * @version: 2012-5-11 下午01:44:41
	 */
	@RequestMapping(value = "/{courseID}/{pageID}",method = RequestMethod.GET)
	@ResponseBody
	public String get(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable int courseID, @PathVariable int pageID)
	{
		return commentManager.get(courseID, pageID, Platform.check(userAgent));
	}
	/**
	 * 
	  * 方法描述：我的评论(有分页)
	  * @author: heshuai
	  * @version: 2012-5-11 下午01:44:41
	 */
	@RequestMapping(value = "/{courseID}/{userID}/{pageID}",method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable int courseID, @PathVariable int userID, @PathVariable int pageID)
	{
		return commentManager.get(courseID, userID, pageID);
	}
	/**
	 * 
	  * 方法描述：该评论下所有回复(无分页)
	  * @author: heshuai
	  * @version: 2012-5-11 下午01:44:41
	 */
	@RequestMapping(value = "/replies/{commentID}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable int commentID)
	{
		return commentManager.get(commentID);
	}
	/**
	 * 
	  * 方法描述：该课程下评论总数
	  * @author: heshuai
	  * @version: 2012-5-11 下午01:44:41
	 */
	@RequestMapping(value = "/{courseID}",method = RequestMethod.GET)
	@ResponseBody
	public String getNum(@PathVariable int courseID)
	{
		return commentManager.getCommentNum(courseID);
	}
	/**
	 * 
	  * 方法描述：增加评论(父评论或者子评论，通过parentID辨别)
	  * @author: heshuai
	  * @version: 2012-5-11 下午01:44:41
	 */
	@RequestMapping(value = "/{courseID}/{userID}",method = RequestMethod.POST)
	@ResponseBody
	public String add(@PathVariable int courseID, 
			@PathVariable int userID, @RequestBody String comment)
	{
		return commentManager.add(comment, courseID, userID);
	}
	/**
	 * 
	  * 方法描述：删除评论(若为父评论则连子评论一并删除)
	  * @author: heshuai
	  * @version: 2012-5-11 下午01:44:41
	 */
	@RequestMapping(value = "/{commentID}",method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable int commentID)
	{
		commentManager.delete(commentID);
	}
}
