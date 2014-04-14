package com.liubaing.galaxy.monitor;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by heshuai on 14-4-14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface MProfiler {

    public abstract String jKey();

    public abstract MEnum[] mState();

}
