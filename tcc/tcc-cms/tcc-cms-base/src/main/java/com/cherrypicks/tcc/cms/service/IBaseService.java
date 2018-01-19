package com.cherrypicks.tcc.cms.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dto.BaseModel;



public interface IBaseService<T extends BaseModel> {

	public T add(T object);

	public T update(T object);
	
	public void delete(T object);

	public void remove(Collection<Object> ids, String updatedBy);
	
	public void delete(Collection<Object> ids);
	
	public T findById(Long key);

	public List<T> findAll();

	public List<T> findByIds(Collection<Object> ids);
	
	public List<? extends Object> findByFilter(Map<String, Object> criteriaMap,String sortField, String sortType,int... args);
}

