package com.liubaing.galaxy.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 类说明:设备令牌 持久化
 *
 * @author heshuai
 * @version 2012-3-12
 */
@Entity
@Table(name = "u_devicetoken_tab")
public class DeviceToken extends BaseModel {

    private static final long serialVersionUID = -7207120063079557078L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;                    //主键

    @Column(nullable = false)
    private int userid;                    //用户ID

    @Column(nullable = false)
    private String devicetoken;            //设备令牌

    private int status;                    //是否允许推送通知 状态   0：不允许   1：允许

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date date;                    //日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
