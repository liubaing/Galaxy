package com.liubaing.galaxy.mq.serializer;


import com.liubaing.galaxy.exception.KafkaException;

/**
 * Kafka消息解码器
 */
public interface KafkaMessageDecoder<V> {

    /**
     * 消息反序列化
     *
     * @param bytes 消息
     * @return 反序列化消息
     * @throws KafkaException
     */
    V decode(byte[] bytes) throws KafkaException;

}
