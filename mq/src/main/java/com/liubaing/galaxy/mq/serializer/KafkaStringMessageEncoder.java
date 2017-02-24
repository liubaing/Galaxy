package com.liubaing.galaxy.mq.serializer;


import com.liubaing.galaxy.exception.KafkaException;
import com.liubaing.galaxy.util.Constants;

import java.io.UnsupportedEncodingException;

/**
 * @author heshuai
 * @version 2017/1/10.
 */
public class KafkaStringMessageEncoder implements KafkaMessageEncoder<String> {

    @Override
    public byte[] encode(String message) throws KafkaException {
        try {
            if (message == null)
                return null;
            else
                return message.getBytes(Constants.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new KafkaException("Error when serializing string to byte[] due to unsupported encoding " + Constants.DEFAULT_CHARSET);
        }
    }

}
