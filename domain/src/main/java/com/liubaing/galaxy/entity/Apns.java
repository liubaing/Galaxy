package com.liubaing.galaxy.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 类说明:推送消息
 *
 * @author heshuai
 * @version 2012-3-12
 */
@Entity
@Table(name = "m_push_tab")
public class Apns extends BaseModel {

    private static final long serialVersionUID = -6204812653027115499L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;                    //主键

    @Column(nullable = false)
    private int recipient;                //通知接受体ID

    private Platform platform;

    private String msgTitle;

    private String msgContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date date;                    //日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }


    /**
     * 类的构造方法
     * 创建一个新的实例 Apns.
     *
     * @param
     */
    public Apns() {
        super();
    }

}
