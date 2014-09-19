package com.liubaing.galaxy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 配置文件
 * 参考Ali团队Code
 *
 * @author heshuai
 */
public class ConfigUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

    private static volatile Properties PROPERTIES;

    public static Properties getProperties() {
        if (PROPERTIES == null) {
            synchronized (ConfigUtils.class) {
                if (PROPERTIES == null) {
                    String path = System.getProperty(Constants.DEFAULT_BOOT_PROPERTIES);
                    if (path == null || path.length() == 0) {
                        path = System.getenv(Constants.DEFAULT_BOOT_PROPERTIES);
                        if (path == null || path.length() == 0) {
                            path = Constants.DEFAULT_BOOT_PROPERTIES;
                        }
                    }
                    PROPERTIES = ConfigUtils.loadProperties(path);
                }
            }
        }
        return PROPERTIES;
    }

    public static void addProperties(Properties properties) {
        if (properties != null) {
            getProperties().putAll(properties);
        }
    }

    public static void setProperties(Properties properties) {
        if (properties != null) {
            PROPERTIES = properties;
        }
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.length() == 0) {
            Properties properties = getProperties();
            value = properties.getProperty(key, defaultValue);
        }
        return value;
    }

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        if (fileName.startsWith("/")) {
            try {
                FileInputStream input = new FileInputStream(fileName);
                try {
                    properties.load(input);
                } finally {
                    input.close();
                }
            } catch (Throwable e) {
                LOGGER.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
            }
            return properties;
        }

        List<java.net.URL> list = new ArrayList<java.net.URL>();
        try {
            Enumeration<java.net.URL> urls = ConfigUtils.class.getClassLoader().getResources(fileName);
            list = new ArrayList<java.net.URL>();
            while (urls.hasMoreElements()) {
                list.add(urls.nextElement());
            }
        } catch (Throwable t) {
            LOGGER.warn("Fail to load " + fileName + " file: " + t.getMessage(), t);
        }

        for (java.net.URL url : list) {
            try {
                Properties p = new Properties();
                InputStream input = url.openStream();
                if (input != null) {
                    try {
                        p.load(input);
                        properties.putAll(p);
                    } finally {
                        try {
                            input.close();
                        } catch (Throwable t) {
                        }
                    }
                }
            } catch (Throwable e) {
                LOGGER.warn("Fail to load " + fileName + " file from " + url + "(ingore this file): " + e.getMessage(), e);
            }
        }

        return properties;
    }

    private ConfigUtils() {
    }

}
