package com.liubaing.galaxy.mq.serializer;


import com.liubaing.galaxy.exception.KafkaException;

/**
 * @author heshuai
 * @version 2017/1/4.
 */
public class KafkaIntegerMessageDecoder implements KafkaMessageDecoder<Integer> {

    @Override
    public Integer decode(byte[] bytes) throws KafkaException {
        if (bytes == null)
            return null;
        if (bytes.length != 4) {
            throw new KafkaException("Size of data received by IntegerDecoder is " +
                    "not 4");
        }

        int value = 0;
        for (byte b : bytes) {
            value <<= 8;
            value |= b & 0xFF;
        }
        return value;
    }
}
