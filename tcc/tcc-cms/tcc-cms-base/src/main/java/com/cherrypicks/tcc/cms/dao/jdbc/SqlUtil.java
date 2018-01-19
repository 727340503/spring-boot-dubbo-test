package com.cherrypicks.tcc.cms.dao.jdbc;

import java.sql.Time;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cherrypicks.tcc.util.DateUtil;

public final class SqlUtil {

    private SqlUtil() {
    }

    public static String getSQL(String sql, final StatementParameter param) {
        int i = 0;
        while (sql.indexOf('?') > -1) {
            if (param == null) {
                throw new RuntimeException("没有设置参数.");
            }
            if (i >= param.size()) {
                return sql;
            }
            String value;
            final int type = param.getTypes(i);

            if (type == Types.VARCHAR) {
                value = "'" + param.getString(i) + "'";
            } else if (type == Types.TIME) {
                value = "'" + getTime(param.getTime(i)) + "'";
            } else if (type == Types.DATE || type == Types.TIMESTAMP) {
                value = "'" + getTime(param.getDate(i)) + "'";
            } else if (type == Types.INTEGER) {
                value = Integer.toString(param.getInt(i));
            } else if (type == Types.FLOAT) {
                value = Float.toString(param.getFloat(i));
            } else if (type == Types.DOUBLE) {
                value = Double.toString(param.getDouble(i));
            } else if (type == Types.BIGINT) {
                value = Long.toString(param.getLong(i));
            } else if (type == Types.NULL) {
                value = null;
            } else {
                throw new RuntimeException("未知数据类型[" + type + "]");
            }

            sql = sql.substring(0, sql.indexOf('?')) + value + sql.substring(sql.indexOf('?') + 1, sql.length());
            i++;
        }
        return sql;
    }

    private static String getTime(final Date date) {
        final DateFormat df = new SimpleDateFormat(DateUtil.DATETIME_PATTERN_DEFAULT);
        return df.format(date);
    }

    private static String getTime(final Time time) {
        final DateFormat df = new SimpleDateFormat(DateUtil.TIME_PATTERN_DEFAULT);
        return df.format(time);
    }

}
