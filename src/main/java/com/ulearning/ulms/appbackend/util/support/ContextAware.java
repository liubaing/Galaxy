package com.ulearning.ulms.appbackend.util.support;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class ContextAware implements ServletContextAware {

	public static ServletContext servletContext;
	 
    @Override
    public void setServletContext(ServletContext servletContext) {
		ContextAware.servletContext = servletContext;
    }
}
