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
public final class ConfigUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

    private static Properties PROPERTIES = new Properties();

    static {
        ConfigUtils.loadProperties(Constants.DEFAULT_BOOT_PROPERTIES);
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }

    public static void addProperties(Properties properties) {
        if (properties != null) {
            getProperties().putAll(properties);
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

    /**
     * 加载配置文件，允许重复加载
     *
     * @param fileName 相对路径于classpath，同样支持绝对路径
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        LOGGER.debug("Loading properties file resource [{}]", fileName);
        if (fileName.startsWith(Constants.PATH_SEPARATOR)) {
            try (FileInputStream input = new FileInputStream(fileName)) {
                properties.load(input);
            } catch (Throwable e) {
                LOGGER.warn("Failed to load [" + fileName + "] file" + e.getMessage(), e);
            }
        } else {
            List<java.net.URL> list = new ArrayList<>();
            try {
                Enumeration<java.net.URL> urls = ConfigUtils.class.getClassLoader().getResources(fileName);
                while (urls.hasMoreElements()) {
                    list.add(urls.nextElement());
                }
            } catch (Throwable e) {
                LOGGER.warn("Fail to load [" + fileName + "] file" + e.getMessage(), e);
            }
            for (java.net.URL url : list) {
                try {
                    try (InputStream input = url.openStream()) {
                        Properties p = new Properties();
                        p.load(input);
                        properties.putAll(p);
                    }
                } catch (Throwable e) {
                    LOGGER.warn("Fail to load " + fileName + " file from " + url + "(ingore this file): " + e.getMessage(), e);
                }
            }
        }
        addProperties(properties);
        return properties;
    }

    private ConfigUtils() {
    }

}
