package com.liubaing.galaxy.worker.consumer;

import com.liubaing.galaxy.mq.consumer.KafkaMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

abstract class TrackProcessor implements KafkaMessageHandler<Integer> {

    protected final Logger logger = LoggerFactory.getLogger(TrackProcessor.class);

    /**
     * 运行状态
     */
    public enum State {

        READY, RUNNING, TERMINAL

    }

    protected boolean power;
    protected State state = State.READY;

    protected TrackProcessor(boolean power) {
        this.power = power;
    }

    protected TrackProcessor() {
    }

    @PostConstruct
    public final void process() {
        if (power) {
            this.state = State.RUNNING;
            logger.info("[" + this.getClass().getSimpleName() + "] running!");
        } else {
            this.state = State.TERMINAL;
        }
    }
}
