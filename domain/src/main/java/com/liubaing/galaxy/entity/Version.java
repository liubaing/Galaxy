package com.liubaing.galaxy.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 更新记录POJO
 * @author heshuai
 * @version 2012-10-29
 *
 */
@Document(collection="updateRecord")
public class Version extends BaseModel {
	
	private static final long serialVersionUID = -3791607982714225119L;
	
	public static final String UPLOAD_VIRTUAL_PATH = "";
	
	private static final Platform DEFAULT_PLATFORM = Platform.ANDROID;
	
	private int versionCode;
	
	private String versionName;
	
	private String Description;
	
	private String url;
	
	private Date Time = new Date();
	
	private Platform platform = DEFAULT_PLATFORM;
	
	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getTime() {
		return Time;
	}

	public void setTime(Date time) {
		Time = time;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
}
