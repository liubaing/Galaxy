package com.liubaing.galaxy.mq.serializer;

import com.liubaing.galaxy.exception.KafkaException;

/**
 * Kafka消息编码器
 */
public interface KafkaMessageEncoder<V> {

    /**
     * 消息序列化
     *
     * @param message 消息
     * @return 序列化消息
     * @throws KafkaException
     */
    byte[] encode(V message) throws KafkaException;

}
