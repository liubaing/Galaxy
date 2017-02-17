package com.liubaing.galaxy.dao;

import com.liubaing.galaxy.exception.MySQLException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 避免Spring依赖传递
 *
 * @param <T>
 */
public abstract class RowMapping<T> implements RowMapper<T> {

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        return doMapping(resultSet, i);
    }

    protected abstract T doMapping(ResultSet rs, int rowNum) throws MySQLException;

}