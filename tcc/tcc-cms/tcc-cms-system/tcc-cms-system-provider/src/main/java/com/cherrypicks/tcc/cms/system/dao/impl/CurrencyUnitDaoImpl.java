package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.system.dao.CurrencyUnitDao;
import com.cherrypicks.tcc.cms.system.model.CurrencyUnit;

@Repository
public class CurrencyUnitDaoImpl extends AbstractBaseDao<CurrencyUnit> implements CurrencyUnitDao {

	@Override
	public List<CurrencyUnit> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

}
