package com.cherrypicks.tcc.cms.dao.jdbc;

import java.util.Locale;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class SimpleBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    public SimpleBeanPropertyRowMapper() {
        super();
    }

    public SimpleBeanPropertyRowMapper(final Class<T> mappedClass) {
        super(mappedClass);
    }

    public SimpleBeanPropertyRowMapper(final Class<T> mappedClass, final boolean checkFullyPopulated) {
        super(mappedClass, checkFullyPopulated);
    }

    @Override
    protected String underscoreName(final String name) {
        final StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());
            for (int i = 1; i < name.length(); i++) {
                final String s = name.substring(i, i + 1);
                if (s.equals(s.toUpperCase(Locale.ENGLISH))) {
                    result.append("_");
                    result.append(s.toLowerCase());
                } else {
                    result.append(s);
                }
            }
        }
        return result.toString();
    }
}
