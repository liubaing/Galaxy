package com.liubaing.galaxy.worker.processor;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author heshuai
 * @version 2017/1/16.
 */
interface Processor extends SimpleJob {

    @Override
    default void execute(ShardingContext shardingContext) {
        this.process();
    }

    void process();

}