package com.liubaing.shiny_liubaing.alipay;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

/**
 * @author heshuai
 * @version 2012-10-10
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@XObject("direct_trade_create_res")
public class DirectTradeCreateRes {

    /**
     * 获得的创建交易的RequestToken
     */
    @XNode("request_token")
    private String requestToken;

    public String getRequestToken() {
        return requestToken;
    }

}
