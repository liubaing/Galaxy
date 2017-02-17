package com.liubaing.galaxy.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通用的工具类
 *
 * @author heshuai
 * @version 15-10-16.
 */
public final class CommonUtils {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    static {
        NUMBER_FORMAT.setGroupingUsed(true);
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    public static String format(double value) {
        return NUMBER_FORMAT.format(value);
    }

    /**
     * 浮点数四舍五入
     *
     * @param value
     * @param scale
     * @return
     */
    public static Double halfUp(Double value, Integer scale) {
        BigDecimal bValue = new BigDecimal(value.toString());
        return bValue.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 可视化字节数
     *
     * @param bytes 字节数
     * @param si    进制
     * @return
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Java Bean 转换为 Map， 注：造这个轮子，原因是new BeanMap，返回值的范型不满足
     *
     * @param instance
     * @return
     */
    public static Map<String, Object> bean2Map(Object instance) {
        return Arrays.stream(instance.getClass().getFields())
                .collect(Collectors.toMap(
                        Field::getName,
                        field -> {
                            try {
                                Object result = field.get(instance);
                                return result != null ? result : "";
                            } catch (Exception e) {
                                return "";
                            }
                        }));
    }
}
