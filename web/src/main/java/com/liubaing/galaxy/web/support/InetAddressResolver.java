package com.liubaing.galaxy.web.support;

import com.liubaing.galaxy.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求IP地址
 *
 * @author heshuai
 * @version 16/7/23.
 */
public class InetAddressResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RemoteAddress.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        RemoteAddress remoteAddress = methodParameter.getParameterAnnotation(RemoteAddress.class);
        if (remoteAddress.required()) {
            String IP = nativeWebRequest.getParameter("cip");
            if (!InetAddressValidator.getInstance().isValidInet4Address(IP)) {
                IP = nativeWebRequest.getHeader("clientip");
                if (!InetAddressValidator.getInstance().isValidInet4Address(IP)) {
                    IP = nativeWebRequest.getHeader("X-Forwarded-For");
                    if (StringUtils.isNotEmpty(IP) && !StringUtils.equalsIgnoreCase("unknown", IP)) {
                        int index = IP.indexOf(Constants.COMMA_SEPARATOR);
                        if (index != -1) {
                            IP = IP.substring(0, index);
                        }
                    } else {
                        IP = nativeWebRequest.getHeader("x-real-ip");
                        if (!InetAddressValidator.getInstance().isValidInet4Address(IP)) {
                            IP = nativeWebRequest.getNativeRequest(HttpServletRequest.class).getRemoteAddr();
                        }
                    }
                }
            }
            return IP;
        }
        return null;
    }
}