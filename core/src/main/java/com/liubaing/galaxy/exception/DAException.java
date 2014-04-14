package com.liubaing.galaxy.exception;

/**
 * @author heshuai
 * @version 2012-10-31
 */
public class DAException extends AppException {

	private static final long serialVersionUID = 5570978578575344671L;
	private int error_code = 1;

	public DAException()
	{
		super();
	}

	public DAException(String msg){
		super(msg);
	}
	
	public DAException(Throwable cause) {
		super(cause);
	}
	
	public DAException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
