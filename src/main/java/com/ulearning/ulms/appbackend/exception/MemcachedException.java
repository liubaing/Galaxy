package com.ulearning.ulms.appbackend.exception;

/**
 * @author heshuai
 * @version 2012-11-1
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class MemcachedException extends ULMSException {
	
	private static final long serialVersionUID = 1996646258389107673L;
	//private int error_code = 2;

	public MemcachedException()
	{
		super();
	}

	public MemcachedException(String msg){
		super(msg);
	}
	
	public MemcachedException(Throwable cause) {
		super(cause);
	}
	
	public MemcachedException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
