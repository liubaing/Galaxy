package com.liubaing.galaxy.mq.consumer;


import com.liubaing.galaxy.exception.KafkaException;

/**
 * Consumer消息回调接口
 *
 * @author heshuai
 * @version 2016/12/7.
 */
public interface KafkaMessageHandler<T> {

    /**
     * 回调
     *
     * @param message 消息
     * @throws KafkaException 异常
     */
    void handle(final T message) throws KafkaException;

    /**
     * Topic
     *
     * @return Consumer订阅的Topic列表
     * @apiNote https://kafka.apache.org/documentation#intro_topics
     */
    String getTopic();

}
