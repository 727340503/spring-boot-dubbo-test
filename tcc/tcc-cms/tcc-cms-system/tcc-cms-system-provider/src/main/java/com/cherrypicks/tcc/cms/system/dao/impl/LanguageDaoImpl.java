package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.system.dao.LanguageDao;
import com.cherrypicks.tcc.cms.system.model.Language;

@Repository
public class LanguageDaoImpl extends AbstractBaseDao<Language> implements LanguageDao {

	@Override
	public List<Language> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

}
