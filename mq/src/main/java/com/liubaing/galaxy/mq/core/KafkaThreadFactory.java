package com.liubaing.galaxy.mq.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Kafka线程工厂类
 *
 * @author heshuai
 * @version 2016/12/7.
 */
public class KafkaThreadFactory implements ThreadFactory {

    private static final String THREAD_NAME_FORMAT = "Kafka-[%s]-%d";

    /**
     * 线程计数器
     */
    private final AtomicInteger i = new AtomicInteger(0);

    /**
     * 线程标示
     */
    private final String prefix;


    public KafkaThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        if (t.isDaemon())
            t.setDaemon(false);
        t.setName(String.format(THREAD_NAME_FORMAT, prefix, i.getAndIncrement()));
        return t;
    }
}
