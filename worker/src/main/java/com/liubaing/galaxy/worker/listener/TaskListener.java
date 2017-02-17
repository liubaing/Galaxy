package com.liubaing.galaxy.worker.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author heshuai
 * @version 2017/1/17.
 */
@Slf4j
public class TaskListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(final ShardingContexts shardingContexts) {
        log.info("beforeJobExecuted:" + shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(final ShardingContexts shardingContexts) {
        log.info("afterJobExecuted:" + shardingContexts.getJobName());
    }
}
