package com.liubaing.shiny_liubaing.util.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 类说明:对象工具类
 * User: heshuai
 * Date: 2012-4-26
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class ObjectUtils {

	/**
	 * 
	  * 方法描述：判断集合是否为空 	<b>true</b>:空
	  * @param: 
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-4-26 下午03:06:35
	 */
	public static boolean isEmpty(List<?> list) {
		boolean isEmpty = true;
		if (list != null && list.size() > 0) {
			isEmpty = false;
		}
		return isEmpty;
	}
	/**
	 * 
	  * 方法描述：判断字符串是否为空	<b>true</b>:空
	  * @param: 
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-4-26 下午03:08:42
	 */
	public static boolean isEmpty(String temp) {
		boolean isEmpty = true;
		if (temp != null && temp != "") {
			isEmpty = false;
		}
		return isEmpty;
	}
	/**
	 * 
	 * 方法描述：判断字符串数组是否为空	<b>true</b>:空
	 * @param: 
	 * @return: 
	 * @author: heshuai
	 * @version: 2012-4-26 下午03:08:42
	 */
	public static boolean isEmpty(String[] temp) {
		boolean isEmpty = true;
		if (temp != null && temp.length > 0) {
			isEmpty = false;
		}
		return isEmpty;
	}
	
	/**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
	/**
	 * 
	  * 方法描述：获取泛型类实际泛型类型
	  * @param: 
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-10-30 上午09:59:39
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Class<T> getGenricClassType(Class clz){
    	Type type = clz.getGenericSuperclass();
    	if(type instanceof ParameterizedType){
    		ParameterizedType pt = (ParameterizedType) type;
    		Type[] types = pt.getActualTypeArguments();
    		if (types.length > 0 && types[0] instanceof Class) {
    			//若有多个泛型类型，根据索引位置返回
				return (Class) types[0];
			}
    	}
    	return (Class) Object.class;
    }
    
    /**
	 * 
	  * 方法描述：生成时间戳
	  * @param: 
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-10-30 上午10:31:41
	 */
	public static String generateTimestamp()
	{
		return  "{\"timestamp\":"+System.currentTimeMillis()+"}";
	}
	
}
