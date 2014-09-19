package com.liubaing.galaxy.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "feedback")
public class Feedback extends BaseModel {

    private static final long serialVersionUID = -133765713642603328L;

    private static final Platform DEFAULT_PLATFORM = Platform.ANDROID;

    private String content;        //内容

    @Field(value = "userid")
    private int userID;            //用户ID

    private Date date;            //时间

    private Platform platform;        //平台版本

    public Feedback() {
        this(null, 0);
    }

    public Feedback(String content, int userID) {
        this(content, userID, new Date(), DEFAULT_PLATFORM);
    }

    public Feedback(String content, int userID, Date date, Platform platform) {
        super();
        this.content = content;
        this.userID = userID;
        this.date = date;
        this.platform = platform;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
