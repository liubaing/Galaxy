package com.liubaing.galaxy.mq.serializer;


import com.liubaing.galaxy.exception.KafkaException;

/**
 * @author heshuai
 * @version 2017/1/4.
 */
public class KafkaIntegerMessageEncoder implements KafkaMessageEncoder<Integer> {

    @Override
    public byte[] encode(Integer message) throws KafkaException {
        if (message == null)
            return null;

        return new byte[]{
                (byte) (message >>> 24),
                (byte) (message >>> 16),
                (byte) (message >>> 8),
                message.byteValue()
        };
    }
}
