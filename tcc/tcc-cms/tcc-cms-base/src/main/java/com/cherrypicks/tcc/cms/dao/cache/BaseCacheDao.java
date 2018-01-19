package com.cherrypicks.tcc.cms.dao.cache;

import java.util.List;
import java.util.Set;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public interface BaseCacheDao<T extends BaseModel> {

    // for default key(id)
    T add(T t);

    boolean del(Long id);

    boolean update(T t);

    T get(Long id);

    boolean expire(T t, int seconds);

    Long ttl(Long id);

    // for DIY key
    T add(String key, T t);

    boolean del(String key);

    boolean update(String key, T t);

    T get(String key);

    boolean expire(String key, int seconds);

    Long ttl(String key);

    // for Native

    // List
    boolean addList(String key, List<T> list);

    boolean delList(final String key);

    boolean listAdd(String key, T t);

    T listGet(String key, long index);

    List<T> getList(String key);

    List<T> getList(String key, int start, int end);

    void ltrimList(String key, int start, int end);

    long getSize(String key);

    boolean delList(String key, T t);

    boolean isEmptyList(String key);

    T listPop(String key);

    // Set
    boolean addSet(String key, String value);

    Set<String> smembers(String key);

    boolean isMember(String key, String value);

    boolean delMember(String key, String value);

    // Hash
    boolean addHash(String key, String field, String value);

    String getHash(String key, String field);

    boolean existsHash(String key, String field);

    boolean delHash(String key, String field);

    // Queue
    boolean addQueue(String key, String... values);

    String getQueue(String key);

    String getBlockQueue(String key);

    // String
    boolean addString(String key, String value);

    String getString(String key);

    // incr
    Long incr(String key);

    Long incrBy(String key, long integer);

    // Lock
    // will lock N seconds if not unlock
    boolean lock(String key, int seconds);
    // unlock
    boolean unlock(final String key);
}
