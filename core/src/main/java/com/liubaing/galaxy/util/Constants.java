package com.liubaing.galaxy.util;

import java.util.regex.Pattern;

public class Constants {

	public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
	
	public final static String  PATH_SEPARATOR  = "/";
	
	public static final String  HIDE_KEY_PREFIX  = ".";

	public static final String  SINGLE_QUOTES  = "'";
	
	public static final String  UNDERLINE  = "_";
	
	public static final String  DEFAULT_BOOT_PROPERTIES = "bootstrap.properties";
	
	public static final int DEFAULT_RESULTS_PER_PAGE = 10;
	
}
