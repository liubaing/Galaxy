package com.liubaing.galaxy.worker.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataProcessor implements Processor {

    private static final String LOG_FMT = "=====> %d.成功%s共[%d]条数据 <=====";

    @Autowired
    private JdbcOperations jdbcOperations;

    private volatile boolean isRunning = false;

    private int step = 1;

    private void print(String info, int count) {
        log.info(String.format(LOG_FMT, step++, info, count));
    }

    public void process() {
        if (!isRunning) {
            try {
                isRunning = true;
            } catch (Exception e) {
                log.error("刷数据异常", e);
            } finally {
                step = 1;
                isRunning = false;
            }
        }
    }
}
