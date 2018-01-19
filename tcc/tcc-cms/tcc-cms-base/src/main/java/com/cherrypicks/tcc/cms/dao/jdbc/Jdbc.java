package com.cherrypicks.tcc.cms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.cherrypicks.tcc.util.ListUtil;

public abstract class Jdbc extends JdbcDaoSupport {

    private final char[] ignoreChar = new char[] {'('};

    @Value("${spring.datasource.schema:}")
    private String schema;

    @Resource
    private DataSource dataSource;

    @PostConstruct
    protected void initDataSource() {
        super.setDataSource(dataSource);
    }

    @PreDestroy
    public void destroy() {
        logger.info("destroy");
    }

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    public NamedParameterJdbcTemplate getSimpleNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 事务回滚.
     */
    public boolean rollback() {
        try {
            final Connection con = super.getConnection();
            if (con == null) {
                return false;
            }
            con.rollback();
            return true;
        } catch (final CannotGetJdbcConnectionException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    protected void printStackTrace(final String sql, final StatementParameter param, final int updatedCount) {
        if (logger.isDebugEnabled()) {
            logger.debug("sql:" + getSQL(sql, param));
        } else {
            logger.info("sql:" + sql);
        }
        logger.info("updatedCount:" + updatedCount);

        final Exception e = new Exception();
        logger.error(e.getMessage(), e);
    }

    protected String getSQL(final String sql, final StatementParameter param) {
        return SqlUtil.getSQL(sql, param);
    }

    private void log(final List<?> list, final String sql, final StatementParameter param) {
        final int size = ListUtil.size(list);

        if (logger.isDebugEnabled()) {
            String sql1;
            if (param == null) {
                sql1 = sql;
            } else {
                sql1 = getSQL(sql, param);
            }

            logger.debug("result size:" + size + " sql:" + sql1);
        } else {
            logger.info("result size:" + size + " sql:" + sql);
        }
    }

    protected int[] batchUpdate(String sql, final List<StatementParameter> paramList) {
        sql = formatSql(sql);

        final BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                setParameterValues(ps, paramList.get(i).getList(), paramList.get(i).getArgTypes());
            }

            @Override
            public int getBatchSize() {
                return paramList.size();
            }
        };

        for (final StatementParameter param : paramList) {
            if (logger.isDebugEnabled()) {
                logger.info("sql:" + getSQL(sql, param));
            } else {
                logger.info("sql:" + sql);
            }
        }
        return getJdbcTemplate().batchUpdate(sql, setter);
    }

    protected <T> T query(String sql, final Class<T> elementType) {
        sql = formatSql(sql);
        try {
            final T t = getJdbcTemplate().queryForObject(sql, new SimpleBeanPropertyRowMapper<T>(elementType));
            int result = 0;
            if (t != null) {
                result = 1;
            }
            logger.info("result:" + result + " sql:" + sql);
            return t;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            logger.info("result:0 sql:" + sql);
            return null;
        }
    }

    protected <T> T query(String sql, final Class<T> elementType, final StatementParameter param) {
        sql = formatSql(sql);
        try {
            final T t = getJdbcTemplate().queryForObject(sql, param.getArgs(),
                    new SimpleBeanPropertyRowMapper<T>(elementType));
            int result = 0;
            if (t != null) {
                result = 1;
            }

            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + getSQL(sql, param));
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return t;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug("result:0 sql:" + getSQL(sql, param));
            } else {
                logger.info("result:0 sql:" + sql);
            }
            return null;
        }
    }

    protected List<Map<String, Object>> queryForMaps(String sql) {
        sql = formatSql(sql);
        try {
            final List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
            log(list, sql, null);
            return list;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected <T> List<T> queryForList(String sql, final Class<T> elementType) {
        sql = formatSql(sql);
        try {
            final List<T> list = getJdbcTemplate().query(sql, new SimpleBeanPropertyRowMapper<T>(elementType));
            log(list, sql, null);
            return list;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected List<Long> queryForLongs(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final List<Long> list = super.getJdbcTemplate().query(sql, param.getArgs(), new RowMapper<Long>() {
            @Override
            public Long mapRow(final ResultSet rs, final int index) {
                try {
                    return rs.getLong(1);
                } catch (final SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        log(list, sql, param);
        return list;
    }

    public List<Integer> queryForInts(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final List<Integer> list = super.getJdbcTemplate().query(sql, param.getArgs(), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(final ResultSet rs, final int index) {
                try {
                    return rs.getInt(1);
                } catch (final SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        log(list, sql, param);
        return list;
    }

    protected List<String> queryForStrings(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final List<String> list = super.getJdbcTemplate().query(sql, param.getArgs(), new RowMapper<String>() {
            @Override
            public String mapRow(final ResultSet rs, final int index) {
                try {
                    return rs.getString(1);
                } catch (final SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        log(list, sql, param);
        return list;
    }

    protected <T> List<T> queryForList(String sql, final Class<T> elementType, final StatementParameter param) {
        sql = formatSql(sql);
        try {
            final List<T> list = getJdbcTemplate().query(sql, param.getArgs(),
                    new SimpleBeanPropertyRowMapper<T>(elementType));
            log(list, sql, param);
            return list;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected long queryForLong(String sql) {
        sql = formatSql(sql);
        try {
            final long result = getJdbcTemplate().queryForObject(sql, Long.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + sql);
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    protected long queryForLong(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final Object[] args = param.getArgs();
        try {
            final long result = getJdbcTemplate().queryForObject(sql, args, Long.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + getSQL(sql, param));
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    public int queryForInt(String sql) {
        sql = formatSql(sql);
        try {
            final int result = getJdbcTemplate().queryForObject(sql, Integer.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + sql);
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    public int queryForInt(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final Object[] args = param.getArgs();
        try {
            final int result = getJdbcTemplate().queryForObject(sql, args, Integer.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + getSQL(sql, param));
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    protected java.util.Date queryForDate(String sql) {
        sql = formatSql(sql);
        try {
            final java.util.Date result = getJdbcTemplate().queryForObject(sql, java.util.Date.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + sql);
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected java.util.Date queryForDate(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final Object[] args = param.getArgs();
        try {
            final java.util.Date result = getJdbcTemplate().queryForObject(sql, args, java.util.Date.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + getSQL(sql, param));
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected String queryForString(String sql) {
        sql = formatSql(sql);
        try {
            final String result = getJdbcTemplate().queryForObject(sql, String.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + sql);
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected String queryForString(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        final Object[] args = param.getArgs();
        try {
            final String result = getJdbcTemplate().queryForObject(sql, args, String.class);
            if (logger.isDebugEnabled()) {
                logger.debug("result:" + result + " sql:" + getSQL(sql, param));
            } else {
                logger.info("result:" + result + " sql:" + sql);
            }
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public boolean updateForBoolean(final String sql, final StatementParameter param) {
        final int updatedCount = updateForRecord(sql, param);
        return (updatedCount > 0);
    }

    protected int updateForRecord(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        if (logger.isDebugEnabled()) {
            logger.debug("sql:" + getSQL(sql, param));
        } else {
            logger.info("sql:" + sql);
        }
        // int updatedCount = getJdbcTemplate().update(sql, param.getParameters());
        final int updatedCount = getJdbcTemplate().update(sql, param.getArgs(), param.getArgTypes());
        logger.info("updatedCount:" + updatedCount);
        return updatedCount;
    }

    protected Long insertForKey(String sql, final StatementParameter param) {
        sql = formatSql(sql);
        if (logger.isDebugEnabled()) {
            logger.debug("sql:" + getSQL(sql, param));
        } else {
            logger.info("sql:" + sql);
        }

        final String excute = sql;
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(excute, new String[] {"id"});

                setParameterValues(ps, param.getList(), param.getArgTypes());

                return ps;
            }
        }, keyHolder);

        logger.info("insertedCount: 1");

        // keyHolder.getKey() now contains the generated key
        return keyHolder.getKey().longValue();
    }

    private void setParameterValues(final PreparedStatement preparedStatement, final List<Object> values,
            final int[] columnTypes) throws SQLException {
        int colIndex = 0;
        for (final Object value : values) {
            colIndex++;
            if (columnTypes == null || colIndex > columnTypes.length) {
                StatementCreatorUtils.setParameterValue(preparedStatement, colIndex, SqlTypeValue.TYPE_UNKNOWN, value);
            } else {
                StatementCreatorUtils.setParameterValue(preparedStatement, colIndex, columnTypes[colIndex - 1], value);
            }
        }
    }

    // protected Long executeAndReturnKey(SimpleStatementParameter param) {
    // SimpleJdbcInsert insertActor = new
    // SimpleJdbcInsert(getJdbcTemplate().getDataSource()).withTableName(param.getTable()).usingGeneratedKeyColumns(param.getKeyColumn());
    // if (log) {
    // logger.info("sql:" + insertActor.getInsertString() + " params:" + param);
    // }
    // Number newId = insertActor.executeAndReturnKey(param.getParameters());
    // if (log) {
    // logger.info("insertedCount: 1");
    // }
    // return newId.longValue();
    // }

    // protected Boolean execute(SimpleStatementParameter param) {
    // SimpleJdbcInsert insertActor = new
    // SimpleJdbcInsert(getJdbcTemplate().getDataSource()).withTableName(param.getTable());
    // Integer row = insertActor.execute(param.getParameters());
    // if (row > 0) {
    // return true;
    // }
    // return false;
    // }

    protected int execute(String sql) {
        sql = formatSql(sql);
        logger.info("sql:" + sql);
        final int updatedCount = getJdbcTemplate().update(sql);
        logger.info("updatedCount:" + updatedCount);
        return updatedCount;
    }

    protected int updateForRecord(String sql) {
        sql = formatSql(sql);
        logger.info(" sql:" + sql);
        final int updatedCount = getJdbcTemplate().update(sql);
        logger.info("updatedCount:" + updatedCount);
        return updatedCount;
    }

    protected int batchUpdateForRecord(final List<String> sqlList) {
        int updatedCount = 0;

        for (final String sql : sqlList) {
            updatedCount += updateForRecord(sql);
        }

        return updatedCount;
    }

    public String beanName() {
        return getClass().getSimpleName();
    }

    protected long queryForLongWithNamedParam(String sql, final Map<String, ?> paramMap) {
        sql = formatSql(sql);
        try {
            final Long result = getSimpleNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Long.class);
            if(null == result){
            	return 0;
            }
            logger.info("result:" + result + " sql:" + sql);
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    protected <T> List<T> queryForListWithNamedParam(String sql, final Class<T> elementType,
            final Map<String, ?> paramMap) {
        sql = formatSql(sql);
        try {
            final List<T> list = getSimpleNamedParameterJdbcTemplate().query(sql, paramMap, new SimpleBeanPropertyRowMapper<T>(elementType));
            log(list, sql, null);
            return list;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected List<String> queryForStringWithNamedParam(String sql, final Map<String, ?> paramMap) {
        sql = formatSql(sql);
        try {
            final List<String> list = getSimpleNamedParameterJdbcTemplate().queryForList(sql, paramMap,
                    String.class);
            log(list, sql, null);
            return list;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    protected int queryForIntWithNamedParam(String sql, final Map<String, ?> paramMap) {
        sql = formatSql(sql);
        try {
            logger.info("sql:" + sql);
            final int result = getSimpleNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
            logger.info("result:" + result);
            return result;
        } catch (final EmptyResultDataAccessException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    protected int updateForRecordWithNamedParam(String sql, final Map<String, ?> paramMap) {
        sql = formatSql(sql);
        logger.info("sql:" + sql);
        final int updatedCount = getSimpleNamedParameterJdbcTemplate().update(sql, paramMap);
        logger.info("updatedCount:" + updatedCount);
        return updatedCount;
    }

    protected String formatSql(String sql) {
        if (StringUtils.isNotBlank(schema)) {
            // remove unnecessary space character
            sql = removeBlank(sql);
            // add schema
            sql = addSchema(sql);
        }

        return sql;
    }

    private String removeBlank(String sql) {
        if (sql.indexOf("  ") > 0) {
            sql = StringUtils.replace(sql, "  ", " ");
            sql = removeBlank(sql);
        }

        return sql;
    }

    private boolean isIgnoreChar(final char c) {
        boolean isIgnoreChar = false;
        for (final char ch : ignoreChar) {
            if (c == ch) {
                isIgnoreChar = true;
                break;
            }
        }
        return isIgnoreChar;
    }

    private String addSchema(String sql) {
        // select, delete
        // " from "
        sql = insertSchemaBy(sql, " (?i)from ", " from ");

        // " join "
        sql = insertSchemaBy(sql, " (?i)join ", " join ");

        // update
        sql = insertSchemaBy(sql, "(?i)update ", " update ");

        // insert into
        sql = insertSchemaBy(sql, "(?i)insert into ", "insert into ");
        return sql;
    }

    private String insertSchemaBy(final String sql, final String regexp, final String keyword) {
        final String[] sqls = sql.split(regexp);
        if (sqls != null && sqls.length > 0) {

            final StringBuilder schemaSql = new StringBuilder();
            for (int i = 0; i < sqls.length; i++) {
                final String tempSql = sqls[i];
                if (i == 0) {
                    schemaSql.append(tempSql);
                } else {
                    schemaSql.append(keyword);

                    if (isIgnoreChar(tempSql.charAt(0))) {
                        schemaSql.append(tempSql);
                        // ignore
                        continue;
                    } else {
                        // insert schema
                        schemaSql.append(schema).append(".");
                        schemaSql.append(tempSql);
                    }
                }
            }
            return schemaSql.toString();
        } else {
            return null;
        }
    }
}
