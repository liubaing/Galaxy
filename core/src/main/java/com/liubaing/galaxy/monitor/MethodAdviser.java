package com.liubaing.galaxy.monitor;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;

/**
 * Created by heshuai on 14-4-14.
 */
@Aspect
public class MethodAdviser implements InitializingBean {

    private String systemKey;
    private String jvmKey;

    @Pointcut(value = "@annotation(MProfiler)")
    public void JAnnotationPoint() {
    }

    @Around(value = "JAnnotationPoint()")
    public Object execJAnnotation(ProceedingJoinPoint jp)
            throws Throwable {
        Method method = getMethod(jp);

        boolean functionerror = false;

        try {
            MProfiler anno = (MProfiler) method.getAnnotation(MProfiler.class);
            if (anno != null) {
                String jproKey = anno.jKey();
                if ((jproKey != null) && (!"".equals(jproKey.trim()))) {
                    boolean tp = false;

                    boolean heartbeat = false;

                    MEnum[] monitorState = anno.mState();

                    for (MEnum me : monitorState) {
                        if (me.equals(MEnum.TP))
                            tp = true;
                        else if (me.equals(MEnum.HEARTBEAT))
                            heartbeat = true;
                        else if (me.equals(MEnum.ERROR)) {
                            functionerror = true;
                        }
                    }

                }

            }

            return jp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
        }
    }

    private Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature) jp.getSignature();
        Method method = msig.getMethod();
        return method;
    }

    public void afterPropertiesSet()
            throws Exception {
        if (StringUtils.isNotBlank(systemKey)) {
        }

        if (StringUtils.isNotBlank(jvmKey)) {
        }
    }

    public void setJvmKey(String jvmKey) {
        this.jvmKey = jvmKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }
}
