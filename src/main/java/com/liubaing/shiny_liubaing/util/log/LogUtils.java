package com.liubaing.shiny_liubaing.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类说明:封装输出日志
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@SuppressWarnings({"rawtypes"})
public final class LogUtils {
	
	//初始化static logger 
	private static Logger logger ;
	
	private static final String ROOT = "root";
	
	private LogUtils(){}

	public enum Level {
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE,
    }
	
	static {
		logger = init(ROOT);
	}
	
	public static Logger init(Class clazz){
		return init(clazz.getName());
	}
	
	public static Logger init(String clazzName) {
	    return LoggerFactory.getLogger(clazzName);
	}
    
    private static boolean isEnabled(final Level level) {
        if (level != null) switch (level) {
            case FATAL: return logger.isErrorEnabled();
            case ERROR: return logger.isErrorEnabled();
            case WARN:  return logger.isWarnEnabled();
            case INFO:  return logger.isInfoEnabled();
            case DEBUG: return logger.isDebugEnabled();
            case TRACE: return logger.isTraceEnabled();
        }
        return true;
    }

    public static void doLog(final String message, final Throwable thrown) {
        doLog(Level.ERROR, null, message, null, thrown);
    }
    
    public static void doLog(final String message, final Object[] parameters, final Throwable thrown) {
    	doLog(Level.ERROR, null, message, parameters, thrown);
    }
    
    /*
     * 变长参数转换底层数组
     */
    public static void doLog(final String message, final Throwable thrown, final Object... parameters) {
    	doLog(Level.ERROR, null, message, parameters, thrown);
    }
    
    public static void doLog(final Level level, final String loggerClassName, final String message, final Object[] parameters, final Throwable thrown) {
        if (isEnabled(level)){
        	try {
        		final String text = parameters == null || parameters.length == 0 ? message : message+createParams(parameters);
        		logger = loggerClassName == null ? logger : init(loggerClassName);
        		switch (level) {
        		case FATAL:
        		case ERROR:
        			logger.error(text, thrown);
        			return;
        		case WARN:
        			logger.warn(text, thrown);
        			return;
        		case INFO:
        			logger.info(text, thrown);
        			return;
        		case DEBUG:
        			logger.debug(text, thrown);
        			return;
        		case TRACE:
        			logger.trace(text, thrown);
        			return;
        		}
        	} catch (Throwable ignored) {}
        }
    }
    
    private static String createParams(Object[] params)
    {
		StringBuffer sb = new StringBuffer(511);
		appendParams( sb, params );
		return sb.toString();
    }

    private static void appendParams(StringBuffer sb, Object[] params)
    {
		sb.append("[params: ");
		for (int i = 0, len = params.length; i < len; ++i)
		    {
				if (i != 0) sb.append(", ");
				sb.append( params[i] );
		    }
		sb.append(']');
    }
    
}
