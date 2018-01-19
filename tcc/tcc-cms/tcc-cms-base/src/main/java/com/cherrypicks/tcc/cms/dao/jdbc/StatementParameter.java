package com.cherrypicks.tcc.cms.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.cherrypicks.tcc.util.DateUtil;

/**
 * SQL参数.
 */
public class StatementParameter {

    private List<Object> list = new ArrayList<Object>();
    
    public void setObject(Object value) {
		if (value == null) {
			throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
		}
		list.add(value);
	}

    public void setDate(final Date value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setString(final String value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setBool(final Boolean value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value ? 1 : 0);
    }

    public void setInt(final Integer value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setLong(final Long value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setDouble(final Double value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setFloat(final Float value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setTime(final Time value) {
        if (value == null) {
            throw new RuntimeException("参数值[" + list.size() + "]不能为空.");
        }
        list.add(value);
    }

    public void setNull() {
        list.add(null);
    }

    public Object[] getArgs() {
        return list.toArray();
    }

    public Date getDate(final int index) {
        final Object value = this.getObject(index);
        return (Date) value;
    }

    public String getString(final int index) {
        final Object value = this.getObject(index);
        return (String) value;
    }

    public int getInt(final int index) {
        final Object value = this.getObject(index);
        return (Integer) value;
    }

    public float getFloat(final int index) {
        final Object value = this.getObject(index);
        return (Float) value;
    }

    public long getLong(final int index) {
        final Object value = this.getObject(index);
        return (Long) value;
    }

    public double getDouble(final int index) {
        final Object value = this.getObject(index);
        return (Double) value;
    }

    public Time getTime(final int index) {
        final Object value = list.get(index);
        return (Time) value;
    }

    public Object getObject(final int index) {
        return list.get(index);
    }

    public int getTypes(final int index) {
        final Object value = list.get(index);
        return this.getTypes(value);
    }

    private int getTypes(final Object value) {
        if (value instanceof String) {
            return Types.VARCHAR;
        } else if (value instanceof Time) {
            return Types.TIME;
        } else if (value instanceof Date) {
            // TIMESTAMP for MySQL
            // DATE for ORACLE/DB2
            return Types.TIMESTAMP;
        } else if (value instanceof Integer) {
            return Types.INTEGER;
        } else if (value instanceof Long) {
            return Types.BIGINT;
        } else if (value instanceof Double) {
            return Types.DOUBLE;
        } else if (value instanceof Float) {
            return Types.FLOAT;
        } else if (value == null) {
            return Types.NULL;
        } else {
            throw new RuntimeException("未知参数类型[" + value + "].");
        }
    }

    public int[] getArgTypes() {
        // { Types.CHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR };
        final int[] valuesTypes = new int[list.size()];
        int i = 0;
        for (final Object value : list) {
            valuesTypes[i] = this.getTypes(value);
            i++;
        }
        return valuesTypes;
    }

    public int size() {
        return list.size();
    }

    public PreparedStatementSetter getParameters() {
        if (list.size() == 0) {
            return null;
        }
        final PreparedStatementSetter param = new PreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement pstmt) {
                try {
                    this.setValues2(pstmt);
                } catch (final SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            public void setValues2(final PreparedStatement pstmt) throws SQLException {
                int i = 1;
                for (final Object value : list) {
                    if (value instanceof String) {
                        pstmt.setString(i, (String) value);
                    } else if (value instanceof Time) {
                        pstmt.setString(i, getTime((Time) value));
                    } else if (value instanceof Date) {
                        pstmt.setString(i, getTime((Date) value));
                    } else if (value instanceof Integer) {
                        pstmt.setInt(i, (Integer) value);
                    } else if (value instanceof Long) {
                        pstmt.setLong(i, (Long) value);
                    } else if (value instanceof Double) {
                        pstmt.setDouble(i, (Double) value);
                    } else if (value instanceof Float) {
                        pstmt.setFloat(i, (Float) value);
                    } else {
                        throw new RuntimeException("未知参数类型[" + i + "." + value + "].");
                    }
                    i++;
                }
            }
        };
        return param;
    }

    private String getTime(final Date date) {
        return DateUtil.formatTime(date);
    }

    private String getTime(final Time time) {
        return DateUtil.getFormatTime(time);
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(final List<Object> list) {
        this.list = list;
    }

}
