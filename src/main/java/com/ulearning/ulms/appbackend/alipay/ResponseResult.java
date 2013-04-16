package com.ulearning.ulms.appbackend.alipay;
/**
 * 类说明:调用支付宝服务返回的结果
 * @author heshuai
 * @version  2012-10-10
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class ResponseResult {

	/**
	 * 是否调用成功 默认为false 所以在每次调用都必须设置这个值为true；
	 */
	private boolean isSuccess = false;
	
	/**
	 * 调用的业务成功结果 如果调用失败 这个将是空值
	 */
	private String businessResult;

	/**
	 * 错误信息
	 */
	private ErrorCode errorMessage;

	public ErrorCode getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorCode errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getBusinessResult() {
		return businessResult;
	}

	public void setBusinessResult(String businessResult) {
		this.businessResult = businessResult;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
