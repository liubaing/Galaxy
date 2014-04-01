package com.liubaing.galaxy.entity;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
/**
 * 
 * 仅作为评论返回值
 * @author heshuai
 * @version 2012-11-29
 */
public class CommentsResult implements Serializable{
	
	private static final long serialVersionUID = -2720461348412551887L;

	@Expose
	private long total;
	
	@Expose
	private List<Comment> comments;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public CommentsResult(long total, List<Comment> comments) {
		super();
		this.total = total;
		this.comments = comments;
	}
	public CommentsResult() {
		super();
	}
}