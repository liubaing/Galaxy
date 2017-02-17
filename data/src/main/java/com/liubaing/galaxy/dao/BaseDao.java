package com.liubaing.galaxy.dao;

import com.liubaing.galaxy.exception.MySQLException;
import com.liubaing.galaxy.mapper.IntegerRowMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class BaseDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JdbcTemplate jdbcTemplate;

    public BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public Connection getConnection() {
        return DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
    }

    protected Long insert(final String sql, final Object[] parameters, final int[] argTypes) throws MySQLException {
        if (StringUtils.isBlank(sql)) {
            return 0L;
        }
        log.debug(sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int rowsAffected = jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        StatementCreatorUtils.setParameterValue(ps, i + 1, argTypes[i], parameters[i]);
                    }
                }
                return ps;
            }, keyHolder);
            if (rowsAffected != 1 || keyHolder.getKey() == null) {
                return null;
            }
        } catch (DataAccessException e) {
            throw new MySQLException(e);
        }
        return keyHolder.getKey().longValue();
    }

    protected int count(String sql, Object[] parameters) {
        if (StringUtils.isBlank(sql)) {
            return 0;
        }
        log.debug(sql);
        return jdbcTemplate.queryForObject(sql, IntegerRowMapper.INSTANCE, parameters);
    }

    protected void execute(final String sql) throws MySQLException {
        log.debug(sql);
        try {
            jdbcTemplate.execute(sql);
        } catch (DataAccessException e) {
            throw new MySQLException(sql, e);
        }
    }

    protected Map<String, Object> findOne(String sql) {
        List<Map<String, Object>> list = this.findForList(sql);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    protected <E> E findOne(String sql, Object[] parameters, int[] argTypes, RowMapping<E> mapper) {
        List<E> list = this.findForList(sql, parameters, argTypes, 0, 1, mapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    protected List<Map<String, Object>> findForList(String sql) {
        if (StringUtils.isBlank(sql)) {
            return null;
        }
        log.debug(sql);
        return jdbcTemplate.queryForList(sql);
    }

    protected <E> List<E> findForList(String sql, RowMapping<E> mapper) {
        if (StringUtils.isBlank(sql)) {
            return null;
        }
        log.debug(sql);
        return jdbcTemplate.query(sql, mapper);
    }

    protected <E> List<E> findForList(String sql, Object[] parameters, int[] argTypes, int curPage, int pageSize,
                                      RowMapping<E> mapper) {
        if (StringUtils.isBlank(sql) || curPage < 0 || pageSize < 0) {
            return null;
        }
        long offset = ((long) curPage) * pageSize;
        sql = sql + " LIMIT " + offset + ", " + pageSize;
        log.debug(sql);
        return jdbcTemplate.query(sql, parameters, argTypes, mapper);
    }
}