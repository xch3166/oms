package com.hrada.oms.service;

import java.util.List;
import java.util.Map;

/**
 * Created by shin on 2018/2/26.
 */
public interface SchemaService {

    List<Map<String, Object>> getTables();
    List<Map<String, Object>> getColumns(String table);
}
