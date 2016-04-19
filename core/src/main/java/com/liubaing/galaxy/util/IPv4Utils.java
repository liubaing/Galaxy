package com.liubaing.galaxy.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.InetAddress;

/**
 * IPv4 工具类
 *
 * @author heshuai
 * @version 16/4/19.
 */
public final class IPv4Utils {

    public static long ip2Long(String ipAddress) {
        String[] ipSplit = StringUtils.split(ipAddress, Constants.DOT_SEPARATOR);
        if (ipSplit.length == 4) {
            long[] ipArr = new long[4];
            ipArr[0] = NumberUtils.toLong(ipSplit[0]);
            ipArr[1] = NumberUtils.toLong(ipSplit[1]);
            ipArr[2] = NumberUtils.toLong(ipSplit[2]);
            ipArr[3] = NumberUtils.toLong(ipSplit[3]);
            return (ipArr[0] << 24) + (ipArr[1] << 16) + (ipArr[2] << 8) + ipArr[3];
        }
        return NumberUtils.LONG_ZERO;
    }

    public static boolean isGlobal(String ipAddress) {
        try {
            if (InetAddressValidator.getInstance().isValidInet4Address(ipAddress)) {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                return !inetAddress.isAnyLocalAddress() && !inetAddress.isLinkLocalAddress()
                        && !inetAddress.isMulticastAddress() && !inetAddress.isLoopbackAddress()
                        && !inetAddress.isSiteLocalAddress();
            }
        } catch (Exception e) {
            //ignore
        }
        return false;
    }
}