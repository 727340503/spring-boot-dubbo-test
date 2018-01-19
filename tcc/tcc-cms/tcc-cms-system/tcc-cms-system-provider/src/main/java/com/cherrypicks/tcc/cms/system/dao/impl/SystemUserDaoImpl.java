package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemUserDao;
import com.cherrypicks.tcc.cms.system.dto.AuthorizeSystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.dto.SystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.dto.SystemUserItemDTO;
import com.cherrypicks.tcc.cms.system.model.SystemUser;

@Repository
public class SystemUserDaoImpl extends AbstractBaseDao<SystemUser> implements SystemUserDao {

	@Override
	public List<SystemUserItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		
		final String name = (String) criteriaMap.get("name");
		final String roleName = (String) criteriaMap.get("roleName");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		String tableAlias = "s";
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(1) FROM SYSTEM_USER s ")
				.append("LEFT JOIN SYSTEM_USER_ROLE_MAP su ON su.USER_ID = s.ID ")
				.append("LEFT JOIN SYSTEM_ROLE sr ON sr.ID = su.ROLE_ID ")
				.append("LEFT JOIN SYSTEM_USER_MERCHANT_MAP sm ON sm.USER_ID = s.ID ")
				.append("LEFT JOIN MERCHANT m ON m.ID = sm.MERCHANT_ID ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = m.ID AND ml.IS_DEFAULT = ? ")
				.append("WHERE s.IS_DELETED = 0 AND su.IS_DELETED = 0 AND sr.IS_DELETED = 0  ");
		
		sql.append("SELECT s.ID,s.USER_NAME,s.STATUS,s.CREATED_TIME,sr.ROLE_NAME,ml.NAME AS MERCHANT_NAME FROM SYSTEM_USER s ")
			.append("LEFT JOIN SYSTEM_USER_ROLE_MAP su ON su.USER_ID = s.ID ")
			.append("LEFT JOIN SYSTEM_ROLE sr ON sr.ID = su.ROLE_ID ")
			.append("LEFT JOIN SYSTEM_USER_MERCHANT_MAP sm ON sm.USER_ID = s.ID ")
			.append("LEFT JOIN MERCHANT m ON m.ID = sm.MERCHANT_ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = m.ID AND ml.IS_DEFAULT = ? ")
			.append("WHERE s.IS_DELETED = 0 AND su.IS_DELETED = 0 AND sr.IS_DELETED = 0 ");
		param.setBool(true);
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append(" AND s.USER_NAME REGEXP ? ");
			sql.append(" AND s.USER_NAME REGEXP ? ");
			
			param.setString(name);
		}
		
		if(StringUtils.isNotBlank(roleName)){
			sqlCount.append(" AND sr.ROLE_NAME REGEXP ? ");
			sql.append(" AND s.ROLE_NAME REGEXP ? ");
			
			param.setString(name);
		}
		
		if(null != merchantId){
			sqlCount.append(" AND sm.MERCHANT_ID REGEXP ? ");
			sql.append(" AND sm.MERCHANT_ID REGEXP ? ");
			
			param.setLong(merchantId);
		}
		
		if("roleName".equalsIgnoreCase(sortField)){
			tableAlias = "sr";
		}
		
		if("merchantName".equalsIgnoreCase(sortField)){
			tableAlias = "ml";
			sortField = "name";
		}
		
		return getPagingList(SystemUserItemDTO.class, sqlCount, sql, param, sortField, sortType, tableAlias, args);
	}

	@Override
	public SystemUser findByUserName(final String userName) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,IS_DELETED,USER_NAME,PASSWORD,STATUS,EMAIL,MOBILE,SESSION FROM SYSTEM_USER WHERE USER_NAME = ? AND IS_DELETED = 0 ");
		param.setString(userName);
		
		return super.query(sql.toString(), SystemUser.class, param);
	}

	@Override
	public void updateUserSession(final String session,final Long userId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("UPDATE SYSTEM_USER SET SESSION = ? WHERE ID = ? ");
		param.setString(session);
		param.setLong(userId);
		
		
		super.updateForBoolean(sql.toString(), param);
	}

	@Override
	public SystemUser findByEmail(final String email) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,IS_DELETED,USER_NAME,PASSWORD,STATUS,EMAIL,MOBILE,SESSION FROM SYSTEM_USER WHERE email = ? AND IS_DELETED = 0 ");
		param.setString(email);
		
		return super.query(sql.toString(), SystemUser.class, param);
	}

	@Override
	public boolean delByIds(List<Long> idList) {
//		final StringBuilder sql = new StringBuilder("DELETE FROM SYSTEM_USER WHERE ID in (:idList)");
		final StringBuilder sql = new StringBuilder("UPDATE SYSTEM_USER SET IS_DELETED = 1 WHERE ID in (:idList)");
        final Map<String, List<Long>> paramMap = Collections.singletonMap("idList", idList);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public SystemUserDetailDTO findById(Long systemUserId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT S.*,SR.ROLE_NAME,SR.ID AS SYSTEM_ROLE_ID,SR.ROLE_TYPE FROM SYSTEM_USER S ")
			.append("LEFT JOIN SYSTEM_USER_ROLE_MAP SUR ON SUR.USER_ID = S.ID ")
			.append("LEFT JOIN SYSTEM_ROLE SR ON SR.ID = SUR.ROLE_ID ")
			.append("WHERE S.IS_DELETED = 0 AND SUR.IS_DELETED = 0 AND SR.IS_DELETED = 0 AND S.ID = ? ");
		param.setLong(systemUserId);
		
		return query(sql.toString(), SystemUserDetailDTO.class, param);
	}

	@Override
	public long findSystemUserCountByRoleIds(List<Object> roleIds) {
		String sql = "select count(ID) from SYSTEM_USER_ROLE_MAP where ROLE_ID in (:roleIds) and IS_DELETED = 0";
		Map<String, List<Object>> paramMap = Collections.singletonMap("roleIds", roleIds);
		return super.queryForLongWithNamedParam(sql, paramMap); 
	}

	@Override
	public AuthorizeSystemUserDetailDTO findAuthorizeUser(Long userId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT S.*,SR.ROLE_NAME,SR.ID AS SYSTEM_ROLE_ID,SR.ROLE_TYPE,SM.MERCHANT_ID FROM SYSTEM_USER S ")
			.append("LEFT JOIN SYSTEM_USER_ROLE_MAP SUR ON SUR.USER_ID = S.ID ")
			.append("LEFT JOIN SYSTEM_ROLE SR ON SR.ID = SUR.ROLE_ID ")
			.append("LEFT JOIN SYSTEM_USER_MERCHANT_MAP SM ON SM.USER_ID = S.ID ")
			.append("WHERE S.IS_DELETED = 0 AND SUR.IS_DELETED = 0 AND SR.IS_DELETED = 0 AND S.ID = ? ");
		param.setLong(userId);
		
		return query(sql.toString(), AuthorizeSystemUserDetailDTO.class, param);
	}

	@Override
	public Integer checkDBConnect(final String tableName) {
		final String sql = "SELECT COUNT(ID) FROM " + tableName;
		return super.queryForInt(sql);
	}

}
