package com.hrada.oms.service.impl;

import com.hrada.oms.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by shin on 2018/2/26.
 */
@Service
public class SchemaServiceImpl implements SchemaService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getTables() {
        String sql = "SELECT TABLE_NAME,TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA = 'oms'";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getColumns(String table) {
        String sql = "select COLUMN_NAME,COLUMN_TYPE,COLUMN_COMMENT from information_schema.COLUMNS where TABLE_NAME = ?1 ";
        return jdbcTemplate.queryForList(sql);
    }
}
