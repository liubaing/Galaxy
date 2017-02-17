package com.liubaing.galaxy.datasource;

/**
 * 线程私有变量记录当前数据源
 *
 * @author heshuai
 * @version 16/4/29.
 */
public class DBContextHolder {

    public static final String DATA_SOURCE_GALAXY = "galaxy";

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceHolder.set(dataSource);
    }

    public static String getDataSourceKey() {
        return dataSourceHolder.get();
    }

    public static void clearDataSourceKey(){
        dataSourceHolder.remove();
    }

}