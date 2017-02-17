package com.liubaing.galaxy.mapper;


import com.liubaing.galaxy.dao.RowMapping;
import com.liubaing.galaxy.exception.MySQLException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerRowMapper extends RowMapping<Integer> {

    public static final IntegerRowMapper INSTANCE = new IntegerRowMapper();

    private IntegerRowMapper() {
    }

    @Override
    protected Integer doMapping(ResultSet rs, int rowNum) throws MySQLException {
        try {
            int value = rs.getInt(1);
            if (rs.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw new MySQLException(e);
        }
    }
}