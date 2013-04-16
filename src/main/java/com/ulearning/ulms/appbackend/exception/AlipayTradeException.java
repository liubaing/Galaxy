package com.ulearning.ulms.appbackend.exception;

/**
 * @author heshuai
 * @version 2012-11-1
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class AlipayTradeException extends ULMSException {
	
	private static final long serialVersionUID = 1996646258389107673L;
	//private int error_code = 3;

	public AlipayTradeException()
	{
		super();
	}

	public AlipayTradeException(String msg){
		super(msg);
	}
	
	public AlipayTradeException(Throwable cause) {
		super(cause);
	}
	
	public AlipayTradeException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
