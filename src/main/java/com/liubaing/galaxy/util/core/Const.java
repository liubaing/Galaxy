package com.liubaing.galaxy.util.core;

/**
 * 类说明:常量
 * @author heshuai
 * @version 2012-3-23
 *
 */
public class Const {

	
	public static final String AUTH_OAUTH2 = "oauth2";
	/*
	 * 订单付款状态
	 */
	public enum payStatus{
		paid     ((short)1,"支付成功"),
		unpaid   ((short)0,"支付失败");
		private short status;
		private String msg;
		private payStatus(short status,String msg)
		{
			this.status = status;
			this.msg = msg;
		}
		public short getStatus() {
			return status;
		}
		public String getMsg() {
			return msg;
		}
	}
}
