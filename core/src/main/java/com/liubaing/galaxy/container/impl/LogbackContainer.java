package com.liubaing.galaxy.container.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.liubaing.galaxy.container.Container;
import com.liubaing.galaxy.util.ConfigUtils;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuai on 14-4-14.
 */
public class LogbackContainer implements Container {

    public static final String LOGBACK_FILE = "logback.file";

    public static final String LOGBACK_LEVEL = "logback.level";

    public static final String LOGBACK_MAX_HISTORY = "logback.maxhistory";

    public static final String DEFAULT_LOGBACK_LEVEL = "ERROR";

    public void start() {
        String file = ConfigUtils.getProperty(LOGBACK_FILE);
        if (file != null && file.length() > 0) {
            String level = ConfigUtils.getProperty(LOGBACK_LEVEL);
            if (level == null || level.length() == 0) {
                level = DEFAULT_LOGBACK_LEVEL;
            }
            // maxHistory=0 Infinite history
            int maxHistory = Integer.parseInt(ConfigUtils.getProperty(LOGBACK_MAX_HISTORY));

            doInitializer(file, level, maxHistory);
        }
    }

    public void stop() {
    }

    /**
     * Initializer logback
     *
     * @param file
     * @param level
     * @param maxHistory
     */
    private void doInitializer(String file, String level, int maxHistory) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.detachAndStopAllAppenders();

        // appender
        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<ILoggingEvent>();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("application");
        fileAppender.setFile(file);
        fileAppender.setAppend(true);

        // policy
        TimeBasedRollingPolicy<ILoggingEvent> policy = new TimeBasedRollingPolicy<ILoggingEvent>();
        policy.setContext(loggerContext);
        policy.setMaxHistory(maxHistory);
        policy.setFileNamePattern(file + ".%d{yyyy-MM-dd}");
        policy.setParent(fileAppender);
        policy.start();
        fileAppender.setRollingPolicy(policy);

        // encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%date [%thread] %-5level %logger (%file:%line\\) - %msg%n");
        encoder.start();
        fileAppender.setEncoder(encoder);

        fileAppender.start();

        rootLogger.addAppender(fileAppender);
        rootLogger.setLevel(Level.toLevel(level));
        rootLogger.setAdditive(false);
    }

}
