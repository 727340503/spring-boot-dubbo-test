package com.cherrypicks.tcc.cms.dao.cache.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.cherrypicks.tcc.cms.dao.jedis.RedisKey;
import com.cherrypicks.tcc.cms.dao.jedis.RedisMapUtil;
import com.cherrypicks.tcc.cms.dto.BaseModel;

public abstract class AbstractBaseCacheDao<T extends BaseModel> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<T> baseModelClass;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        logger.info("init");
        this.baseModelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @PreDestroy
    public void destroy() {
        logger.info("destroy");
    }

    protected String getDefaultKey(final Long id) {
        return new StringBuilder().append(RedisKey.ENTITY_PREFIX).append(RedisKey.DELIMITER).append(baseModelClass.getSimpleName())
                .append(RedisKey.DELIMITER).append(id).toString();
    }

    public T add(final T object) {
        final String key = getDefaultKey(object.getId());

        return add(key, object);
    }

    public T add(final String key, final T object) {
        final Map<String, String> hash = RedisMapUtil.toMap(object);

        if (hash != null && !hash.isEmpty()) {
            stringRedisTemplate.opsForHash().putAll(key, hash);
        }

        return object;
    }

    public boolean del(final Long id) {
        return del(getDefaultKey(id));
    }

    public boolean del(final String key) {
        if (key.indexOf("?") > 0 || key.indexOf("*") > 0 || (key.indexOf("[") > 0 && key.indexOf("]") > 0)) {
            final Set<String> keys = stringRedisTemplate.keys(key);
            if (keys == null || keys.isEmpty()) {
                return true;
            }

            final Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                stringRedisTemplate.delete(it.next());
            }

            return true;
        } else {
            stringRedisTemplate.delete(key);
            return true;
        }
    }

    public boolean update(final T object) {
        add(object);

        return true;
    }

    public boolean update(final String key, final T object) {
        add(key, object);

        return true;
    }

    public boolean expire(final T object, final int seconds) {
        return stringRedisTemplate.expire(getDefaultKey(object.getId()), seconds, TimeUnit.SECONDS);
    }

    public boolean expire(final String key, final int seconds) {
        return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    public T get(final Long id) {
        final String key = getDefaultKey(id);
        final Map<Object, Object> hash = stringRedisTemplate.opsForHash().entries(key);

        T t = null;
        try {
            if (hash != null && !hash.isEmpty()) {
                t = (T) RedisMapUtil.toBean(baseModelClass, hash);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

        return t;
    }

    @SuppressWarnings("unchecked")
    public T get(final String key) {
        final Map<Object, Object> hash = stringRedisTemplate.opsForHash().entries(key);

        T t = null;
        try {
            if (hash != null && !hash.isEmpty()) {
                t = (T) RedisMapUtil.toBean(baseModelClass, hash);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

        return t;
    }

    public Long ttl(final Long id) {
        final String key = getDefaultKey(id);

        return ttl(key);
    }

    public Long ttl(final String key) {
        return stringRedisTemplate.getExpire(key);
    }

    public boolean addList(final String key, final List<T> list) {
        del(key);

        boolean result = false;
        if (list != null && !list.isEmpty()) {

            for (final T t : list) {
                add(t);
                stringRedisTemplate.opsForList().rightPush(key, String.valueOf(t.getId()));
            }

            result = true;
        }

        return result;
    }

    public boolean delList(final String key) {
        return del(key);
    }

    public boolean listAdd(final String key, final T t) {
        if (get(t.getId()) == null) {
            add(t);
        }

        stringRedisTemplate.opsForList().rightPush(key, String.valueOf(t.getId()));

        return true;
    }

    public T listGet(final String key, final long index) {
        final String idStr = stringRedisTemplate.opsForList().index(key, index);
        if (StringUtils.isBlank(idStr)) {
            return null;
        }
        final long id = Long.parseLong(idStr);

        return get(id);
    }

    public List<T> getList(final String key) {
        // get all
        return getList(key, 0, -1);
    }

    public List<T> getList(final String key, final int start, final int stop) {
        List<T> list = null;
        final List<String> ids = stringRedisTemplate.opsForList().range(key, start, stop);

        if (ids != null && !ids.isEmpty()) {
            list = new ArrayList<T>();
            for (final String idStr : ids) {
                // using the default key to get the object
                if(StringUtils.isBlank(idStr) || StringUtils.equals("null", idStr)) {
                    continue;
                }
                final long id = Long.parseLong(idStr);
                final T t = get(id);
                list.add(t);
            }
        }

        return list;
    }

    public T listPop(final String key) {
        final String idStr = stringRedisTemplate.opsForList().leftPop(key);
        if (StringUtils.isNotEmpty(idStr)) {
            final long id = Long.parseLong(idStr);
            final T t = get(id);
            return t;
        } else {
            return null;
        }
    }

    public void ltrimList(final String key, final int start, final int stop) {
        stringRedisTemplate.opsForList().trim(key, start, stop);
    }

    public long getSize(final String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    public boolean delList(final String key, final T t) {
        stringRedisTemplate.opsForList().remove(key, 1, String.valueOf(t.getId()));
        return true;
    }

    public boolean isEmptyList(final String key) {
        return getSize(key) > 0;
    }

    public boolean addSet(final String key, final String member) {
        stringRedisTemplate.opsForSet().add(key, member);
        return true;
    }

    public Set<String> smembers(final String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    public boolean isMember(final String key, final String member) {
        return stringRedisTemplate.opsForSet().isMember(key, member);
    }

    public boolean delMember(final String key, final String value) {
        stringRedisTemplate.opsForSet().remove(key, value);
        return true;
    }

    public boolean addHash(final String key, final String field, final String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
        return true;
    }

    public String getHash(final String key, final String field) {
        String result = null;
        try {
            result =  (String) stringRedisTemplate.opsForHash().get(key, field);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public boolean existsHash(final String key, final String field) {
        return stringRedisTemplate.opsForHash().get(key, field) != null;
    }

    public boolean delHash(final String key, final String field) {
        stringRedisTemplate.opsForHash().delete(key, field);
        return true;
    }

    public boolean addQueue(final String key, final String... values) {
        stringRedisTemplate.opsForList().leftPushAll(key, values);
        return true;
    }

    public String getQueue(final String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    public String getBlockQueue(final String key) {
        return stringRedisTemplate.opsForList().rightPop(key, 0, TimeUnit.SECONDS);
    }

    public boolean addString(final String key, final String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return true;
    }

    public String getString(final String key) {
    	String result = null;
    	try {
    		result = stringRedisTemplate.opsForValue().get(key);
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
    	return result;

    }

    public Long incr(final String key) {
        return stringRedisTemplate.opsForValue().increment(key, 1);
    }

    public Long incrBy(final String key, final long integer) {
        return stringRedisTemplate.opsForValue().increment(key, integer);
    }

    public boolean lock(final String key, final int seconds) {
        if (seconds <= 0) {
            return false;
        }

        if (stringRedisTemplate.opsForValue().setIfAbsent(key, "LOCKED")) {
            expire(key, seconds);
            return true;
        } else {
            return false;
        }
    }

    public boolean unlock(final String key) {
        return del(key);
    }
}
