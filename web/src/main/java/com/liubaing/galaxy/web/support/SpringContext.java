package com.liubaing.galaxy.web.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取指定的实例
     * 常规下，不建议使用
     * @param name 实例别名
     * @return 需转型
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static String getMessage(String code) {
        return applicationContext.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
