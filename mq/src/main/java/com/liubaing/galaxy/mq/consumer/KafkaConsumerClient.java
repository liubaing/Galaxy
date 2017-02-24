package com.liubaing.galaxy.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.liubaing.galaxy.exception.ValidateException;
import com.liubaing.galaxy.mq.core.KafkaConstants;
import com.liubaing.galaxy.mq.core.KafkaThreadFactory;
import com.liubaing.galaxy.mq.serializer.KafkaMessageDecoder;
import com.liubaing.galaxy.util.ConfigUtils;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Kafka消费者客户端
 * 0.8.x High Level版本
 *
 * @author heshuai
 * @version 2016/11/21.
 */
@Slf4j
public class KafkaConsumerClient<V> {

    private KafkaMessageDecoder<V> messageDecoder;

    private final List<KafkaMessageHandler<V>> kafkaMessageHandlerList = new CopyOnWriteArrayList<>();

    public KafkaConsumerClient(KafkaMessageDecoder<V> messageDecoder, List<KafkaMessageHandler<V>> kafkaMessageHandlerList) {
        this("config-kafka-consumer.properties", messageDecoder, kafkaMessageHandlerList);
    }

    public KafkaConsumerClient(String configFilePath, KafkaMessageDecoder<V> messageDecoder, List<KafkaMessageHandler<V>> kafkaMessageHandlerList) {
        if (messageDecoder == null) {
            messageDecoder = bytes -> JSON.parseObject(new String(bytes), new TypeReference<V>() {
            });
        }
        this.messageDecoder = messageDecoder;
        Properties properties = ConfigUtils.loadProperties(configFilePath);
        if (properties.getProperty(KafkaConstants.GROUP_ID) == null) {
            throw new ValidateException(String.format("Kafka配置文件[%s]不存在", configFilePath));
        }
        if (CollectionUtils.isEmpty(kafkaMessageHandlerList)) {
            throw new ValidateException("kafkaMessageHandlerList must have elements");
        }
        this.kafkaMessageHandlerList.addAll(kafkaMessageHandlerList);
        this.setUp(properties);
    }

    public void setUp(Properties props) {
        ConsumerConfig config = new ConsumerConfig(props);
        ConsumerConnector consumerConn =
                kafka.consumer.Consumer.createJavaConsumerConnector(config);
        Map<String, Integer> topicCountMap = new HashMap<>();
        Map<String, KafkaMessageHandler<V>> messageHandlerMap = Maps.newHashMap();
        kafkaMessageHandlerList.stream().forEach(messageHandler -> {
                    String topic = messageHandler.getTopic();
                    topicCountMap.put(topic, 2);
                    messageHandlerMap.put(topic, messageHandler);
                }
        );
        Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamsMap =
                consumerConn.createMessageStreams(topicCountMap);
        topicStreamsMap.keySet().stream().forEach(topic -> {
                    List<KafkaStream<byte[], byte[]>> streams = topicStreamsMap.get(topic);
                    ExecutorService executor = Executors.newFixedThreadPool(2, new KafkaThreadFactory(topic));
                    for (int i = 0; i < streams.size(); i++) {
                        executor.submit(new ReceiverThread(streams.get(i), messageHandlerMap.get(topic)));
                    }
                }
        );
        Runtime.getRuntime().addShutdownHook(new Thread("Kafka-Consumer-Shutdown") {
            public void run() {
                consumerConn.commitOffsets();
                consumerConn.shutdown();
            }
        });
        log.info("Kafka Consumer initialization completed!");
    }

    class ReceiverThread implements Runnable {

        private KafkaStream<byte[], byte[]> stream;
        private KafkaMessageHandler<V> messageHandler;

        public ReceiverThread(KafkaStream<byte[], byte[]> stream, KafkaMessageHandler<V> messageHandler) {
            this.stream = stream;
            this.messageHandler = messageHandler;
        }

        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + " start.");
            try {
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                while (it.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> data = it.next();
                    try {
                        log.debug("Receive message topic: " + data.topic()
                                + " offset: " + data.offset()
                                + " partition: " + data.partition());
                        messageHandler.handle(messageDecoder.decode(data.message()));
                    } catch (Exception e) {
                        log.warn("Handle message failed.", e);
                    }
                }
            } catch (Exception e) {
                log.error("Receive message failed.", e);
            }
            log.info(Thread.currentThread().getName() + " end.");
        }
    }
}
