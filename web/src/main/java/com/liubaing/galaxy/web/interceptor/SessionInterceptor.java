package com.liubaing.galaxy.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.liubaing.galaxy.util.Constants;
import com.liubaing.galaxy.web.vo.ResponseVo;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (WebUtils.getSessionAttribute(request, Constants.ACCOUNT_KEY) == null) {
            ServletOutputStream out = response.getOutputStream();
            IOUtils.write(JSON.toJSONString(new ResponseVo(ResponseVo.ErrorCode.SESSION_EXPIRED)), out, Constants.DEFAULT_CHARSET);
            response.setContentType(ContentType.APPLICATION_JSON.withCharset(Constants.DEFAULT_CHARSET).toString());
            try {
                out.flush();
            } finally {
                IOUtils.closeQuietly(out);
            }
            return false;
        }
        return true;
    }
}
