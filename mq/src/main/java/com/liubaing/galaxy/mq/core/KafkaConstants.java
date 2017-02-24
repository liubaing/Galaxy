package com.liubaing.galaxy.mq.core;

/**
 * Kafka配置常量参数
 *
 * @author heshuai
 * @version 2016/12/7.
 */
public interface KafkaConstants {

    String BROKER_LIST = "bootstrap.servers";
    String GROUP_ID = "group.id";
    String ENABLE_AUTO_COMMIT = "enable.auto.commit";

    long DEFAULT_POLL_TIMEOUT_MS = 10 * 1000;
}