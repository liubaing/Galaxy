package com.liubaing.galaxy.mq.serializer;


import com.liubaing.galaxy.exception.KafkaException;
import com.liubaing.galaxy.util.Constants;

import java.io.UnsupportedEncodingException;

/**
 * @author heshuai
 * @version 2017/1/10.
 */
public class KafkaStringMessageDecoder implements KafkaMessageDecoder<String> {

    @Override
    public String decode(byte[] bytes) throws KafkaException {
        try {
            if (bytes == null)
                return null;
            else
                return new String(bytes, Constants.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new KafkaException("Error when deserializing byte[] to string due to unsupported encoding " + Constants.DEFAULT_CHARSET);
        }
    }
}
