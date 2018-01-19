package com.cherrypicks.tcc.cms.dao.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.dao.jdbc.Jdbc;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.dto.BaseModel;
import com.cherrypicks.tcc.cms.dto.OrderDTO;
import com.cherrypicks.tcc.cms.exception.RecordVersionException;
import com.cherrypicks.tcc.util.Json;
import com.cherrypicks.tcc.util.paging.ArrayPagingList;
import com.cherrypicks.tcc.util.paging.PagingList;
import com.cherrypicks.tcc.util.paging.Sort;

@Transactional(readOnly = true)
public abstract class AbstractBaseDao<T extends BaseModel> extends Jdbc {

    private static final String MYSQL_DIALECT = "MySQLDialect";
    private static final String DB2_DIALECT = "DB2Dialect";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Class<T> baseModelClass;
    protected String tableName;

    @Value("${jdbc.dialect:" + MYSQL_DIALECT + "}")
    private String dialect;

    private boolean existBaseTable;
    private String baseTableIdName;

    @Override
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        super.init();

        this.baseModelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.tableName = underscoreName(baseModelClass.getSimpleName());
        // system_info_lang => system_info_id
        if (isExistBaseTable()) {
            existBaseTable = true;
            this.baseTableIdName = new StringBuilder(this.tableName.substring(0, this.tableName.lastIndexOf("_") + 1))
                    .append("id").toString();
        }
    }

    private boolean isExistBaseTable() {
        return this.tableName.lastIndexOf("_") != -1;
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case. Any upper case letters are converted to lower
     * case with a preceding underscore.
     *
     * @param name
     *            the string containing original name
     * @return the converted name
     */
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
        return result.toString().toUpperCase();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T add(final T object) {

        final SqlStatementParameter sqlStatementParameter = addSqlStatementParameter(object);
        object.setId(super.insertForKey(sqlStatementParameter.getSql(), sqlStatementParameter.getParam()));

        return object;
    }

    @SuppressWarnings("rawtypes")
    private SqlStatementParameter addSqlStatementParameter(final T object) {
        final StringBuilder sql = new StringBuilder("INSERT INTO ").append(this.tableName).append("(");
        final StringBuilder placeholder = new StringBuilder();
        final StatementParameter param = new StatementParameter();

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(baseModelClass);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);

        boolean flag = false;
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final String underscoredName = underscoreName(propertyName);
            final Class propertyType = beanWrapper.getPropertyType(propertyName);
            final Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (!propertyName.equals("class")) {
                if (flag) {
                    sql.append(",");
                    placeholder.append(",");
                }
                sql.append(underscoredName);
                placeholder.append("?");

                if (propertyValue != null) {
                    if (propertyType.equals(Time.class)) {
                        param.setTime((Time) propertyValue);
                    } else if (propertyType.equals(Date.class)) {
                        param.setDate((Date) propertyValue);
                    } else if (propertyType.equals(String.class)) {
                        param.setString((String) propertyValue);
                    } else if (propertyType.equals(Boolean.class)) {
                        param.setBool((Boolean) propertyValue);
                    } else if (propertyType.equals(Integer.class)) {
                        param.setInt((Integer) propertyValue);
                    } else if (propertyType.equals(Long.class)) {
                        param.setLong((Long) propertyValue);
                    } else if (propertyType.equals(Double.class)) {
                        param.setDouble((Double) propertyValue);
                    } else if (propertyType.equals(Float.class)) {
                        param.setFloat((Float) propertyValue);
                    } else {
                        param.setString(propertyValue.toString());
                    }
                } else {
                    param.setNull();
                }
                flag = true;
            }
        }

        sql.append(") VALUES(");
        sql.append(placeholder);
        sql.append(")");

        return new SqlStatementParameter(sql.toString(), param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean del(final T object) {
        return del(object.getId());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int delAll() {
        final StringBuilder sql = new StringBuilder("DELETE FROM ").append(this.tableName);
        return super.execute(sql.toString());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean del(final Long id) {
        final StringBuilder sql = new StringBuilder("DELETE FROM ").append(this.tableName).append(" WHERE id = ?");
        final StatementParameter param = new StatementParameter();
        param.setLong(id);
        return super.updateForBoolean(sql.toString(), param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean delByIds(final Collection<Object> keys) {
        final StringBuilder sql = new StringBuilder("DELETE FROM ").append(this.tableName)
                .append(" WHERE ID in (:keys)");
        final Map<String, Collection<Object>> paramMap = Collections.singletonMap("keys", keys);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean update(final T object) {
        final SqlStatementParameter updateSqlStatementParameter = updateSqlStatementParameter(object);

        if (updateSqlStatementParameter.getParam().size() > 0) {
            return super.updateForBoolean(updateSqlStatementParameter.getSql(), updateSqlStatementParameter.getParam());
        } else {
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    private SqlStatementParameter updateSqlStatementParameter(final T object) {
        final StringBuilder sql = new StringBuilder("UPDATE ").append(this.tableName).append(" SET");
        final StatementParameter param = new StatementParameter();

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(baseModelClass);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        boolean flag = false;
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final String underscoredName = underscoreName(propertyName);
            final Class propertyType = beanWrapper.getPropertyType(propertyName);
            final Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (!propertyName.equals("id") && !propertyName.equals("class")) {
                if (flag) {
                    sql.append(",");
                }

                sql.append(" " + underscoredName + "=?");
                if (propertyValue != null) {

                    if (propertyType.equals(Time.class)) {
                        param.setTime((Time) propertyValue);
                    } else if (propertyType.equals(Date.class)) {
                        param.setDate((Date) propertyValue);
                    } else if (propertyType.equals(String.class)) {
                        param.setString((String) propertyValue);
                    } else if (propertyType.equals(Boolean.class)) {
                        param.setBool((Boolean) propertyValue);
                    } else if (propertyType.equals(Integer.class)) {
                        param.setInt((Integer) propertyValue);
                    } else if (propertyType.equals(Long.class)) {
                        param.setLong((Long) propertyValue);
                    } else if (propertyType.equals(Double.class)) {
                        param.setDouble((Double) propertyValue);
                    } else if (propertyType.equals(Float.class)) {
                        param.setFloat((Float) propertyValue);
                    } else {
                        param.setString(propertyValue.toString());
                    }
                } else {
                    param.setNull();
                    // sql.append(" " + underscoredName + "=NULL");
                }

                flag = true;
            }
        }

        sql.append(" WHERE id=?");
        param.setLong(object.getId());

        return new SqlStatementParameter(sql.toString(), param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean updateSpecifyField(final T object) {
        final SqlStatementParameter updateSqlStatementParameter = updateSpecifyFieldSqlStatementParameter(object);

        if (updateSqlStatementParameter.getParam().size() > 0) {
            return super.updateForBoolean(updateSqlStatementParameter.getSql(), updateSqlStatementParameter.getParam());
        } else {
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    private SqlStatementParameter updateSpecifyFieldSqlStatementParameter(final T object) {
        final StringBuilder sql = new StringBuilder("UPDATE ").append(this.tableName).append(" SET");
        final StatementParameter param = new StatementParameter();

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(baseModelClass);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        boolean flag = false;
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final String underscoredName = underscoreName(propertyName);
            final Class propertyType = beanWrapper.getPropertyType(propertyName);
            final Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (null !=propertyValue  && !propertyName.equals("id") && !propertyName.equals("class")) {
                if (flag) {
                    sql.append(",");
                }

                sql.append(" " + underscoredName + "=?");
//                if (propertyValue != null) {

                    if (propertyType.equals(Time.class)) {
                        param.setTime((Time) propertyValue);
                    } else if (propertyType.equals(Date.class)) {
                        param.setDate((Date) propertyValue);
                    } else if (propertyType.equals(String.class)) {
                        param.setString((String) propertyValue);
                    } else if (propertyType.equals(Boolean.class)) {
                        param.setBool((Boolean) propertyValue);
                    } else if (propertyType.equals(Integer.class)) {
                        param.setInt((Integer) propertyValue);
                    } else if (propertyType.equals(Long.class)) {
                        param.setLong((Long) propertyValue);
                    } else if (propertyType.equals(Double.class)) {
                        param.setDouble((Double) propertyValue);
                    } else if (propertyType.equals(Float.class)) {
                        param.setFloat((Float) propertyValue);
                    } else {
                        param.setString(propertyValue.toString());
                    }
//                }
//                else {
//                    param.setNull();
//                    // sql.append(" " + underscoredName + "=NULL");
//                }

                flag = true;
            }
        }


    sql.append(" WHERE id=?");
    param.setLong(object.getId());

    return new SqlStatementParameter(sql.toString(), param);
}


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T get(final Long id) {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName).append(" WHERE id = ?  AND IS_DELETED = 0 ");
        final StatementParameter param = new StatementParameter();
        param.setLong(id);
        return super.query(sql.toString(), this.baseModelClass, param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<T> findByBaseId(final Long id) {
        if (existBaseTable) {
            final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName).append(" WHERE ")
                    .append(this.baseTableIdName).append(" = ?  AND IS_DELETED = 0 ");
            final StatementParameter param = new StatementParameter();
            param.setLong(id);
            return super.queryForList(sql.toString(), this.baseModelClass, param);
        } else {
            logger.error("Base Table not found");
            return null;
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T getByLang(final Long id, final String lang) {
        if (existBaseTable) {
            final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName).append(" WHERE ")
                    .append(this.baseTableIdName).append(" = ? AND LANG_CODE = ? AND IS_DELETED = 0 ");
            final StatementParameter param = new StatementParameter();
            param.setLong(id);
            param.setString(lang);
            return super.query(sql.toString(), this.baseModelClass, param);
        } else {
            logger.error("Base Table not found");
            return null;
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<T> findAll() {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName).append(" WHERE IS_DELETED = 0 ");
        return super.queryForList(sql.toString(), this.baseModelClass);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<T> findByIds(final Collection<Object> keys) {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName)
                .append(" WHERE ID in (:keys) AND IS_DELETED = 0 ");
        final Map<String, Collection<Object>> paramMap = Collections.singletonMap("keys", keys);
        return super.queryForListWithNamedParam(sql.toString(), this.baseModelClass, paramMap);
    }

    /**
     * Get paging list
     *
     * @param object
     * @param criteria
     * @param args
     * @return
     */
    @SuppressWarnings("hiding")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public <T> PagingList<T> queryForPagingList(final String sql, final Class<T> elementType,
            StatementParameter param, final Sort sort, final int... args) {
        final PagingList<T> resultList = new ArrayPagingList<T>();

        if (args == null || args.length < 2) {
            resultList.addAll(queryForList(sql, elementType, param));
            return resultList;
        }

        int startRecord = 0;
        if (args[0] > 0) {
            startRecord = args[0];
        }
        int maxRecords = 1;
        if (args[1] > 1) {
            maxRecords = args[1];
        }

        final int page = startRecord / maxRecords;

        final StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM (").append(sql).append(") a");
        if (param == null) {
            param = new StatementParameter();
        }
        final int totalRecords = super.queryForInt(countSql.toString(), param);

        StringBuilder pagingSql = new StringBuilder("SELECT a.* FROM (").append(sql).append(") a");

        pagingSql.append(" ORDER BY ").append(underscoreName(sort.getColumn())).append(" ").append(sort.getSortType());

        if (MYSQL_DIALECT.equals(dialect)) {
            pagingSql.append(" LIMIT ?, ?");
            param.setInt(startRecord);
            param.setInt(maxRecords);
        } else if (DB2_DIALECT.equals(dialect)) {
            final StringBuilder db2sql = new StringBuilder();
            db2sql.append("select * from (select ROW_NUMBER() OVER() AS ROWNUM, source.*  from (");
            db2sql.append(sql);
            db2sql.append(") source ) a where ROWNUM > ? and ROWNUM <= ?");

            pagingSql = db2sql;
            param.setInt(startRecord);
            param.setInt(startRecord + maxRecords);
        }

        resultList.setStartRecord(startRecord);
        resultList.setMaxRecords(maxRecords);
        resultList.setTotalRecords(totalRecords);
        resultList.setPage(page);

        resultList.setTotalPages(
                new BigDecimal(totalRecords).divide(new BigDecimal(maxRecords), BigDecimal.ROUND_UP).intValue());

        resultList.addAll(super.queryForList(pagingSql.toString(), elementType, param));

        return resultList;
    }

    @SuppressWarnings("hiding")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public <T> PagingList<T> queryForPagingList(final String sql, final Class<T> elementType,
            StatementParameter param, final int... args) {
        final PagingList<T> resultList = new ArrayPagingList<T>();

        if (args == null || args.length < 2) {
            resultList.addAll(queryForList(sql, elementType, param));
            return resultList;
        }

        int startRecord = 0;
        if (args[0] > 0) {
            startRecord = args[0];
        }
        int maxRecords = 1;
        if (args[1] > 1) {
            maxRecords = args[1];
        }

        final int page = startRecord / maxRecords;

        final StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM (").append(sql).append(") a");
        if (param == null) {
            param = new StatementParameter();
        }
        final int totalRecords = super.queryForInt(countSql.toString(), param);

        StringBuilder pagingSql = new StringBuilder("SELECT a.* FROM (").append(sql).append(") a");

        if (MYSQL_DIALECT.equals(dialect)) {
            pagingSql.append(" LIMIT ?, ?");
            param.setInt(startRecord);
            param.setInt(maxRecords);
        } else if (DB2_DIALECT.equals(dialect)) {
            final StringBuilder db2sql = new StringBuilder();
            db2sql.append("select * from (select ROW_NUMBER() OVER() AS ROWNUM, source.*  from (");
            db2sql.append(sql);
            db2sql.append(") source ) a where ROWNUM > ? and ROWNUM <= ?");

            pagingSql = db2sql;
            param.setInt(startRecord);
            param.setInt(startRecord + maxRecords);
        }

        resultList.setStartRecord(startRecord);
        resultList.setMaxRecords(maxRecords);
        resultList.setTotalRecords(totalRecords);
        resultList.setPage(page);

        resultList.setTotalPages(
                new BigDecimal(totalRecords).divide(new BigDecimal(maxRecords), BigDecimal.ROUND_UP).intValue());

        resultList.addAll(super.queryForList(pagingSql.toString(), elementType, param));

        return resultList;
    }

    protected String getOrderBySql(final Sort[] sorts, final String alias) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" ORDER BY ");
        boolean flag = false;
        for (final Sort sort : sorts) {
            if (flag) {
                sql.append(", ");
            }

            if (StringUtils.isNotBlank(alias)) {
                sql.append(alias).append(".");
            }
            sql.append(underscoreName(sort.getColumn()));
            if (Sort.SortType.DESC.toStringValue().equals(sort.getSortType())) {
                sql.append(" DESC");
            }
            flag = true;
        }

        return sql.toString();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int batchAdd(final List<T> list) {
        int iRet = 0;
        String sql = "";
        final List<StatementParameter> paramList = new ArrayList<StatementParameter>();

        if (list != null && !list.isEmpty()) {
            for (final T t : list) {
                final SqlStatementParameter addSqlStatementParameter = addSqlStatementParameter(t);
                sql = addSqlStatementParameter.getSql();
                paramList.add(addSqlStatementParameter.getParam());
            }

            final int[] result = super.batchUpdate(sql, paramList);

            if (result != null && result.length > 0) {
                for (final int i : result) {
                    iRet += i;
                }
            }
        }

        return iRet;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int batchUpdate(final List<T> list) {
        int iRet = 0;
        String sql = "";
        final List<StatementParameter> paramList = new ArrayList<StatementParameter>();

        if (list != null && !list.isEmpty()) {
            for (final T t : list) {
                final SqlStatementParameter updateSqlStatementParameter = updateSqlStatementParameter(t);
                sql = updateSqlStatementParameter.getSql();
                paramList.add(updateSqlStatementParameter.getParam());
            }

            final int[] result = super.batchUpdate(sql, paramList);

            if (result != null && result.length > 0) {
                for (final int i : result) {
                    iRet += i;
                }
            }
        }

        return iRet;
    }

    private class SqlStatementParameter {
        private String sql;
        private StatementParameter param;

        SqlStatementParameter(final String sql, final StatementParameter param) {
            setSql(sql);
            setParam(param);
        }

        public String getSql() {
            return sql;
        }

        public void setSql(final String sql) {
            this.sql = sql;
        }

        public StatementParameter getParam() {
            return param;
        }

        public void setParam(final StatementParameter param) {
            this.param = param;
        }

    }

    protected String jsonToSql(final String orderStr, final String[] columnName) {
        if (!StringUtils.isNotBlank(orderStr)) {
            return StringUtils.EMPTY;
        }
        final List<OrderDTO> orderList = stringToList(orderStr);
        final StringBuilder sql = new StringBuilder(" ORDER BY ");
        if (orderList.size() == 0) {
            return StringUtils.EMPTY;
        } else {
            for (int i = 0; i < orderList.size(); i++) {
                if (i != 0) {
                    sql.append(", ");
                }
                final OrderDTO order = orderList.get(i);
                sql.append(columnName[order.getColumn()]).append(" ").append(order.getDir());
            }
        }

        return sql.toString();
    }

    private List<OrderDTO> stringToList(final String orderStr) {
        final List<OrderDTO> orderList = new ArrayList<OrderDTO>();
        final String newOrderStr = orderStr.replace("%7B", "{").replace("%7D", "}").replace("%22", "\"");

        if (newOrderStr != null && !newOrderStr.isEmpty()){
            final OrderDTO order = Json.toObject(orderStr, OrderDTO.class);
            orderList.add(order);
        }

        return orderList;
    }
    
    /**
	 * Get paging list
	 * @param object
	 * @param criteria
	 * @param args
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T getPagingList(Class<?> object, StringBuilder sqlCount, StringBuilder sql, StatementParameter param, int... args) {
		final PagingList<Object> resultList = new ArrayPagingList<Object>();
		
		if (args == null || args.length < 2) {
			if (null != param) {
				resultList.addAll(super.queryForList(sql.toString(), object, param));
			} else {
				resultList.addAll(super.queryForList(sql.toString(), object));
			}
			return (T) resultList;
		}
		
		final int startRecord = args[0];
		final int maxRecords = args[1];
		
		int page = startRecord / maxRecords;
		Long totalRecords = (null != param) ? (long) super.queryForInt(sqlCount.toString(), param) : super.queryForInt(sqlCount.toString());
		
		// set startRecord and maxRecord to sql
		sql.append(" LIMIT ?, ? ");
		if (null != param) {
			param.setInt(startRecord);
			param.setInt(maxRecords);
		} else {
			param = new StatementParameter();
			param.setInt(startRecord);
			param.setInt(maxRecords);
		}
		
		resultList.setStartRecord(startRecord);
		resultList.setMaxRecords(maxRecords);
		resultList.setTotalRecords(totalRecords);
		resultList.setPage(page);
		resultList.setTotalPages(new BigDecimal(totalRecords).divide(new BigDecimal(maxRecords), BigDecimal.ROUND_UP).intValue());
		resultList.addAll(super.queryForList(sql.toString(), object, param));
		
//		if (log) {
//			logger.info("result size:" + resultList.size() + " sql:" + this.getSQL(sql.toString(), param));
//		}
		return (T) resultList;
	}
	
	/**
	 * Get paging list and sort Field
	 * @param object
	 * @param criteria
	 * @param args
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T getPagingList(Class<?> object, StringBuilder sqlCount, StringBuilder sql, StatementParameter param,String sortField,String sortType,String tableAlias,int... args) {
		if (StringUtils.isNotEmpty(sortField)) {
			 sortField = underscoreName(sortField);
			if(tableAlias != null){
			  sortField = tableAlias+"."+sortField;
			} 
			if ("DESC".equals(sortType)) {
				//sqlCount.append(" ORDER BY "+sortField+" DESC ");
				sql.append(" ORDER BY "+sortField+" DESC ");
			} else {
				//sqlCount.append(" ORDER BY "+sortField+" ASC ");
				sql.append(" ORDER BY "+sortField+" ASC ");
			}
		}
		return (T) getPagingList(object, sqlCount, sql, param, args);
	}

	
	@SuppressWarnings("rawtypes")
	public T updateForVersion(T object) {
		StringBuilder sql = new StringBuilder("UPDATE ").append(this.tableName).append(" SET");
		StatementParameter param = new StatementParameter();

		final Date currentDate = new Date();
		
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(baseModelClass);
		BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
		for (PropertyDescriptor pd : pds) {
			String propertyName = pd.getName();
			String underscoredName = underscoreName(propertyName);
			Class propertyType = beanWrapper.getPropertyType(propertyName);
			Object propertyValue = beanWrapper.getPropertyValue(propertyName);

			if (/*propertyValue != null &&*/!propertyName.equals("id") && !propertyName.equals("class")) {
				if (param.size() > 0) {
					sql.append(",");
				}
				sql.append(" " + underscoredName + "=?");

				if (propertyValue == null && propertyName.equals("updatedTime")) {
					param.setDate(currentDate);
				} else if (propertyValue == null) {
					param.setNull();
				} else if (propertyType.equals(Date.class)) {
			    	if (propertyName.equals("updatedTime")) {
   	    				param.setDate(currentDate);
   	    			}else{
   	    				param.setDate((Date) propertyValue);
   	    			}
				} else if (propertyType.equals(String.class)) {
					param.setString((String) propertyValue);
				} else if (propertyType.equals(Boolean.class)) {
					param.setBool((Boolean) propertyValue);
				} else if (propertyType.equals(Integer.class)) {
					param.setInt((Integer) propertyValue);
				} else if (propertyType.equals(Long.class)) {
					param.setLong((Long) propertyValue);
				} else if (propertyType.equals(Double.class)) {
					param.setDouble((Double) propertyValue);
				} else if (propertyType.equals(Float.class)) {
					param.setFloat((Float) propertyValue);
				} else if (propertyType.equals(Object.class)) {
					param.setObject(propertyValue);
				}
			} /*else if (propertyValue == null && propertyName.equals("updatedTime")) {
				if (param.size() > 0) {
					sql.append(",");
				}
				sql.append(" " + underscoredName + "=?");
				param.setDate(currentDate);
			}*/
		}
		
		sql.append(" WHERE ID=?");
		param.setObject(object.getId());
		
		if (null != object.getUpdatedTime()) {
			sql.append(" AND (UPDATED_TIME IS NULL OR UPDATED_TIME = ? ) ");
			param.setDate(object.getUpdatedTime());
		} 

		boolean updatedSuccess = false;
		if (param.size() > 0) {
			updatedSuccess = super.updateForBoolean(sql.toString(), param);
		} else {
			updatedSuccess = false;
		}
		
		if (updatedSuccess) {
			object.setUpdatedTime(currentDate);
		}
		
		if (!updatedSuccess && null != object.getUpdatedTime()) {
   			throw new RecordVersionException();
   		}
   		
		return updatedSuccess ? object : null;
	}

}
