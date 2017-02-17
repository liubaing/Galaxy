package com.liubaing.galaxy.web.support;

import java.lang.annotation.*;

/**
 * IP地址
 *
 * @author heshuai
 * @version 16/7/23.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteAddress {

    boolean required() default true;

}
