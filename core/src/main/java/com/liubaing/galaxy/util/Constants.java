package com.liubaing.galaxy.util;

import java.util.regex.Pattern;

/**
 * 全局常量，避免重复定义
 */
public class Constants {

    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
    public static final String COMMA_SEPARATOR = ",";
    public static final String SEMICOLON_SEPARATOR = ";";
    public static final String HYPHEN = "-";
    public static final String PATH_SEPARATOR = "/";
    public static final String HIDE_KEY_PREFIX = ".";
    public static final String SINGLE_QUOTES = "'";
    public static final String UNDERLINE = "_";

    public static final String DEFAULT_BOOT_PROPERTIES = "bootstrap.properties";

    public static final String DEFAULT_CHARSET = "UTF-8";

}
