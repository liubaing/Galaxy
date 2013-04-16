package com.ulearning.ulms.appbackend.alipay;

import java.io.InputStream;

import org.nuxeo.common.xmap.XMap;

/**
 * 类说明:XMap工具类，用来解析xml文件 
 * @author heshuai
 * @version 2012-10-10
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class XMapUtil {
	
    private static final XMap xmap;

    static {
        xmap = new XMap();
    }

    /**
     * 注册Object。
     * 
     * @param clazz
     */
    public static void register(Class<?> clazz) {
        if (clazz != null) {
            xmap.register(clazz);
        }
    }

    /**
     * 解析xml到Object
     * @param is
     * @return
     * @throws Exception
     */
    public static Object load(InputStream is) throws Exception {
        Object obj = null;
        try {
            obj = xmap.load(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return obj;
    }

}

