package com.liubaing.shiny_liubaing.oauth;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

import com.liubaing.shiny_liubaing.util.log.LogUtils;

/**
 * 类说明:生成{@code MD5}加密的token
 * @author heshuai
 * @version 2012-5-2
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Component
public class TokenGenerator{

	 public String generateValue(String param){
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch (Exception e) {
        	LogUtils.doLog("生成MD5加密的token失败", e, param);
        }
        return null;
    }
}
