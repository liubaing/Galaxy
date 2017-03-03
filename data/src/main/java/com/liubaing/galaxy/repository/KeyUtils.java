package com.liubaing.galaxy.repository;

import com.liubaing.galaxy.util.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * Redis中key生成
 *
 * @author heshuai
 * @version 9/26/14.
 */
abstract class KeyUtils {

    static final String PREFIX = "galaxy:";

    static String combine(Object... keys) {
        return StringUtils.join(keys, Constants.HYPHEN);
    }

    static String account() {
        return PREFIX + "account";
    }

}