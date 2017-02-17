package com.liubaing.galaxy.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源切换
 *
 * @author heshuai
 * @version 16/4/29.
 */
public class MultipleDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDataSourceKey();
    }
}