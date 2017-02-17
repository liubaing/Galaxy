package com.liubaing.galaxy.util;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author heshuai
 * @version 9/27/14.
 */
public class IDGenerator {

    //TODO 调整为HASH
    public static String generate() {
        return StringUtils.replace(UUID.randomUUID().toString(), Constants.HYPHEN, "");
    }

    public static int generate(String id) {
        if (StringUtils.isEmpty(id)) {
            return org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
        }
        return Hashing.murmur3_32().hashString(id, Charset.defaultCharset()).asInt();
    }
}
