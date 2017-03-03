package com.liubaing.galaxy.worker.consumer;

import com.liubaing.galaxy.exception.KafkaException;
import com.liubaing.galaxy.mq.core.Topics;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理登陆事件
 *
 * @author heshuai
 * @version 15-10-30.
 */
@Slf4j
public final class TrackLoginProcessor extends TrackProcessor {

    public TrackLoginProcessor(boolean power) {
        super(power);
    }

    public TrackLoginProcessor() {
    }

    @Override
    public void handle(Integer accountId) throws KafkaException {
        try {
            log.info("账户{}登陆", accountId);
            //增加积分 etc
        } catch (Exception e) {
        }
    }

    @Override
    public String getTopic() {
        return Topics.TOPIC_LOGIN;
    }

}