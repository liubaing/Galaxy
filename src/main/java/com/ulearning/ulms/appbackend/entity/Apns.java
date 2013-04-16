package com.ulearning.ulms.appbackend.entity;

import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ulearning.ulms.appbackend.service.apns.IPush;

/**
 * 
 * 类说明:推送消息
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Document(collection="apns")
public class Apns extends BaseModel {
	
	private static final long serialVersionUID = -6204812653027115499L;

	@Id
	private ObjectId id;
	
	private int recipient;				//通知接受体ID

	private IPush pushType;
	
	private Set<Platform> platforms = new HashSet<Platform>(); 
	
	private MsgContent msgContent = new MsgContent();

	public class MsgContent {
		private String title = "";
		private String message = "";

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		public MsgContent() {
			super();
		}
		public MsgContent(String title, String message) {
			super();
			this.title = title;
			this.message = message;
		}
		@Override
		public String toString() {
			return title + message;
		}
	}
	
	private IOSExtra extra;
	
	public class IOSExtra {

		public IOSExtra(int badge, String sound) {
			this.badge = badge;
			this.sound = sound;
		}

		public IOSExtra(String sound) {
			this.sound = sound;
		}

		public IOSExtra(int badge) {
			this.badge = badge;
		}

		/*
		 * Badge Notification,默认是(0)
		 */
		private int badge = 0;
		/*
		 *  当前软件里面的所拥有的铃声名称（如：message.wav)。
		 *  不设置，手机默认通知铃声
		 */
		private String sound = ""; 

		public int getBadge() {
			return badge;
		}

		public void setBadge(int badge) {
			this.badge = badge;
		}

		public String getSound() {
			return sound;
		}

		public void setSound(String sound) {
			this.sound = sound;
		} 

	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	

	public ObjectId getId() {
		return id;
	}
	
	public int getRecipient() {
		return recipient;
	}

	public void setRecipient(int recipient) {
		this.recipient = recipient;
	}
	
	public Set<Platform> getPlatforms() {
		return platforms;
	}

	public void addPlatform(Platform platform) {
		this.platforms.add(platform);
	}

	public void setPushType(IPush pushType) {
		this.pushType = pushType;
	}

	public IPush getPushType() {
		return pushType;
	}

	public void setExtra(IOSExtra extra) {
		this.extra = extra;
	}

	public IOSExtra getExtra() {
		return extra;
	}

	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}

	public MsgContent getMsgContent() {
		return msgContent;
	}
	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 Apns.
	  * @param 
	  * @param recipient
	  * @param pushType
	  * @param msgContent
	  * @param extra
	  */
	public Apns(int recipient, IPush pushType) {
		super();
		this.recipient = recipient;
		this.pushType = pushType;
	}

	/**
	  * 类的构造方法
	  * 创建一个新的实例 Apns.
	  * @param 
	  */
	public Apns() {
		super();
	}
}
