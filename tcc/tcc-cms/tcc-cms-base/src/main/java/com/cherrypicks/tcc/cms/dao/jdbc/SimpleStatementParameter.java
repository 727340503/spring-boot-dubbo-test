package com.cherrypicks.tcc.cms.dao.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SQL参数
 */
public class SimpleStatementParameter {

    private final Map<String, Object> map = new HashMap<String, Object>();

    private String table;
    private String keyColumn;

    public SimpleStatementParameter(final String table, final String keyColumn) {
        this.table = table;
        this.keyColumn = keyColumn;
    }

    public String getTable() {
        return table;
    }

    public void setTable(final String table) {
        this.table = table;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(final String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public void setDate(final String column, final Date value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value);
    }

    public void setString(final String column, final String value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value);
    }

    public void setBool(final String column, final Boolean value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value ? 1 : 0);
    }

    public void setInt(final String column, final Integer value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value);
    }

    public void setLong(final String column, final Long value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value);
    }

    public void setDouble(final String column, final Double value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value);
    }

    public void setFloat(final String column, final Float value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + map.size() + "]不能为空.");
        }
        map.put(column, value);
    }

    public Map<String, Object> getParameters() {
        return map;
    }
}
