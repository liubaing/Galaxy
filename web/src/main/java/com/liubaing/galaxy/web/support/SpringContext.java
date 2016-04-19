package com.liubaing.galaxy.web.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

public final class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取指定的实例
     * 常规下，不建议使用
     *
     * @param requiredType 类，接口
     * @return 范型，无需转换
     */
    public <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static String getMessage(String code) {
        return applicationContext.getMessage(code, null, LocaleContextHolder.getLocale());
    }

}
