package com.liubaing.shiny_liubaing.exception;

/**
 * 类说明:appbackend所有异常基类，运行时异常不强制捕捉
 * @author heshuai
 * @version 2012-11-2
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class ULMSException extends RuntimeException{
	
	private static final long serialVersionUID = -2837703628855700262L;

	public ULMSException()
	{
		super();
	}

	public ULMSException(String msg){
		super(msg);
	}
	
	public ULMSException(Throwable cause) {
		super(cause);
	}
	
	public ULMSException(String msg, Throwable cause) {
		super(msg,cause);
	} 
}
