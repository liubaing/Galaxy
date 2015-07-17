package com.liubaing.galaxy.worker.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redis 消息监听
 *
 * @author heshuai
 * @version 15-1-25.
 */
public class MessageDelegate {

    private final Logger logger = LoggerFactory.getLogger(MessageDelegate.class);

    public void handleMessage(String message, String channelName) {
        logger.info("channel:" + channelName + ", message:" + message);
    }
}
