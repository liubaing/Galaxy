package com.liubaing.galaxy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Until;

/**
 * 类说明:评论实体
 * @author heshuai
 * @version Feb 24, 2012
 *
 */
@Entity
@Table(name = "u_comments_tab")
public class Comment extends BaseModel implements Comparable<Comment>
{
	
	public static final int DEFAULT_COMMENT_PARENT_ID = 0;

	private static final long serialVersionUID = 7852402582005868780L;

	@Id
	@Expose
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  id;       			//id主键
	
	@Expose(serialize = false)
	@Column(nullable = false)
	private int parentid;				//父评论ID			

	
	@Column(nullable = false)
	private int userid ;				//用户ID
	
	@Until(1.1)
	@Expose
	@Transient
	private long sub_num;				//子评论数目

	@Expose
	private String text ;    			//评论内容

	@Expose
	private String username ;      		//用户姓名
	
	@Expose
	@SerializedName(value="date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date createDate;			//日期

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public long getSub_num() {
		return sub_num;
	}

	public void setSub_num(long sub_num) {
		this.sub_num = sub_num;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public boolean equals(Object obj) { 
        if (this == obj) 
            return true; 
        if (obj == null) 
            return false; 
        if (getClass() != obj.getClass()) 
            return false; 
        final Comment other = (Comment) obj;
         if(other.getId().equals(this.getId())) 
            return true; 
        return false; 
   }

	@Override
	public int compareTo(Comment o) {
		return new CompareToBuilder().
		append(o.getId(),id).toComparison();
	} 
	
}
