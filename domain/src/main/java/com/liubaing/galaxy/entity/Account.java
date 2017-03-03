package com.liubaing.galaxy.entity;

import java.util.Date;

/**
 * 账户基本信息
 *
 * @author heshuai
 */
public class Account {

    public int id;
    public String email;
    public String password;
    public String name;
    public String phone;
    public Integer state;
    public Date createDate;
    public Date editDate;

    public enum State {

        DISABLE(0), AVAILABLE(1);

        int code;

        State(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public String getId() {
        return String.valueOf(id);
    }
}
