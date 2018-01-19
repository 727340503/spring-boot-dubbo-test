package com.cherrypicks.tcc.cms.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dto.BaseModel;



public interface IBaseDao<T extends BaseModel> {

    T add(T t);

    boolean del(Long id);

    boolean delByIds(Collection<Object> keys);

    boolean update(T t);
    
    T updateForVersion(T object);

    T get(Long id);

    List<T> findAll();

    List<T> findByBaseId(Long baseId);

    T getByLang(Long id, String lang);

    List<T> findByIds(Collection<Object> ids);

    int batchAdd(List<T> list);

    int batchUpdate(List<T> list);
    
    public List<? extends Object> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args);
}
