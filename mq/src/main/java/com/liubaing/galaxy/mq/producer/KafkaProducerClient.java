package com.liubaing.galaxy.mq.producer;

import com.alibaba.fastjson.JSON;
import com.liubaing.galaxy.exception.ValidateException;
import com.liubaing.galaxy.mq.core.KafkaConstants;
import com.liubaing.galaxy.mq.serializer.KafkaMessageEncoder;
import com.liubaing.galaxy.util.ConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Kafka生产者者客户端
 * 回退到 0.8.x 版本
 *
 * @author heshuai
 * @version 2016/12/6.
 */
@Slf4j
public class KafkaProducerClient<V> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final boolean autoFlush;

    private Producer<String, byte[]> producer;

    private KafkaMessageEncoder<V> encoder;

    private KafkaProducerClient(String configFilePath, boolean autoFlush) {
        this(configFilePath, autoFlush, null);
    }

    public KafkaProducerClient(KafkaMessageEncoder<V> encoder) {
        this("config-kafka-producer.properties", true, encoder);
    }

    public KafkaProducerClient(String configFilePath, boolean autoFlush, KafkaMessageEncoder<V> encoder) {
        Properties props = ConfigUtils.loadProperties(configFilePath);
        if (props.getProperty(KafkaConstants.BROKER_LIST) == null) {
            throw new ValidateException(String.format("Kafka配置文件[%s]不存在", configFilePath));
        }
        this.autoFlush = autoFlush;
        this.producer = new KafkaProducer<>(props);
        if (encoder == null) {
            encoder = message -> JSON.toJSONBytes(message);
        }
        this.encoder = encoder;
        Runtime.getRuntime().addShutdownHook(new Thread("Kafka-Producer-Shutdown") {
            public void run() {
                shutDown();
            }
        });
        log.info("Kafka Producer initialization completed!");
    }

    public void send(String topic, V value) {
        this.doSend(topic, null, value, sendCallback);
    }

    public void sendWithKey(String topic, String key, V value) {
        this.doSend(topic, key, value, sendCallback);
    }

    private void doSend(String topic, String key, V value, Callback callback) {
        logger.trace("--Kafka-- topic[{}] message[{}]", topic, value);
        if (value == null) {
            throw new ValidateException("value is required and cannot be empty");
        }
        ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(topic, key, encoder.encode(value));
        this.producer.send(producerRecord, callback);
        if (!autoFlush) {
            //this.producer.flush(); 保留0.10.x版本支持
        }
    }

    public void shutDown() {
        this.producer.close();
    }

    protected Callback sendCallback = (metadata, exception) -> {
        if (exception != null) {
            logger.error("Failed to send message to kafka ex: ", exception);
        } else {
            logger.debug("--Kafka-- topic[{}] offset[{}]", metadata.topic(), metadata.offset());
        }
    };
}
