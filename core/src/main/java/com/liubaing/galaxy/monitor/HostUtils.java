package com.liubaing.galaxy.monitor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by heshuai on 14-4-14.
 */
public class HostUtils {

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static final String STR_HOST_ERROR_DETECTED = "** HOST ERROR DETECTED **";
    private static final String STR_IP_ERROR_DETECTED = "** IP ERROR DETECTED **";
    private static final String LOG_TIME_FORMAT = "yyyyMMddHHmmssSSS";
    public static final long UPDATETIME = 43200000L;
    public static final int ALIVETIME = 20000;
    public static final int JVMTIME_R = 10000;
    public static final int JVMTIME_E = 14400000;
    public static Boolean SYSTEM_HEART_INIT = Boolean.valueOf(false);

    public static Boolean JVM_MONITOR_INIT = Boolean.valueOf(false);
    public static final String QUOTATION = "\"";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String EXTENSIVE = "1";
    public static final String NONEXTENSIVE = "0";
    public static final Map<String, Long> FUNC_HB = new HashMap();

    public static final String HOST_NAME = getHostName();
    public static final String HOST_IP = getHostIP();

    private static String getHostName() {
        String host = "** HOST ERROR DETECTED **";
        try {
            try {
                InetAddress localAddress = InetAddress.getLocalHost();
                host = localAddress.getHostName();
            } catch (Throwable e) {
                InetAddress localAddress = getLocalAddress();
                if (localAddress != null)
                    host = localAddress.getHostName();
                else {
                    host = "** HOST ERROR DETECTED **";
                }
            }
        } catch (Throwable ex) {
        }
        return host;
    }

    public static String getHostIP() {
        String ip = "** IP ERROR DETECTED **";
        try {
            if (getLocalAddress() != null)
                ip = getLocalAddress().getHostAddress();
            else {
                ip = "** IP ERROR DETECTED **";
            }
        } catch (Throwable ex) {
        }
        return ip;
    }

    public static String getNowTime() {
        String nowTime = null;
        try {
            Date rightNow = new Date();
            DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            nowTime = format1.format(rightNow);
        } catch (Exception e) {
            nowTime = "";
        }
        return nowTime;
    }

    public static String changeLongToDate(long time) {
        String nowTime = null;
        try {
            Date date = new Date(time);
            DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            nowTime = format1.format(date);
        } catch (Exception e) {
            nowTime = "";
        }
        return nowTime;
    }

    public static String getLocalIP() {
        InetAddress address = getLocalAddress();
        return address == null ? null : address.getHostAddress();
    }

    private static InetAddress getLocalAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
        }
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null)
                while (interfaces.hasMoreElements())
                    try {
                        NetworkInterface network = (NetworkInterface) interfaces.nextElement();
                        Enumeration addresses = network.getInetAddresses();
                        if (addresses != null)
                            while (addresses.hasMoreElements())
                                try {
                                    InetAddress address = (InetAddress) addresses.nextElement();
                                    if (isValidAddress(address))
                                        return address;
                                } catch (Throwable e) {
                                }
                    } catch (Throwable e) {
                    }
        } catch (Throwable e) {
        }
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if ((address == null) || (address.isLoopbackAddress())) {
            return false;
        }

        String ip = address.getHostAddress();

        return (ip != null) && (!"0.0.0.0".equals(ip)) && (!"127.0.0.1".equals(ip)) && (IP_PATTERN.matcher(ip).matches());
    }
}
