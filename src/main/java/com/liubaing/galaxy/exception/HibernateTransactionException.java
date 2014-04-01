package com.liubaing.galaxy.exception;

/**
 * Spring管理事务 该异常触发事务回滚
 * This exception indicates that the transaction rolled back
 * @author heshuai
 * @version 2012-10-31
 */
public class HibernateTransactionException extends AppException {

	private static final long serialVersionUID = 5570978578575344671L;
	//private int error_code = 1;

	public HibernateTransactionException()
	{
		super();
	}

	public HibernateTransactionException(String msg){
		super(msg);
	}
	
	public HibernateTransactionException(Throwable cause) {
		super(cause);
	}
	
	public HibernateTransactionException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
