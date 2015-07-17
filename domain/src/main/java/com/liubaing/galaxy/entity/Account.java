package com.liubaing.galaxy.entity;

import java.util.Date;

/**
 * 账户基本信息
 *
 * @author heshuai
 */
public class Account {

    public String accountId;
    public String email;
    public String password;
    public Date createDate;
    public Date editDate;
    public int state;

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

}
