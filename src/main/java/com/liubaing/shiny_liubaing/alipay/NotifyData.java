package com.liubaing.shiny_liubaing.alipay;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

/**
 * 类说明:交易状态信息
 * @author heshuai
 * @version 2012-10-10
 * 
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@XObject("notify")
public class NotifyData {
    /**
     * 订单编号
     */
    @XNode("out_trade_no")
    private String out_trade_no;

    /**
     * 交易状态
     */
    @XNode("trade_status")
    private String trade_status;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}


}
