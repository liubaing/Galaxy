package com.liubaing.galaxy.domain;

/**
 * 系统平台
 *
 * @author heshuai
 */
public enum Platform {

    IOS("IOS"),

    ANDROID("ANDROID");

    private String desc;

    private Platform(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public static Platform fromDesc(String desc) {
        for (Platform p : Platform.values()) {
            if (p.desc.equalsIgnoreCase(desc)) {
                return p;
            }
        }
        throw new IllegalArgumentException("'" + desc + "'" + " is not a valid description");
    }
}
