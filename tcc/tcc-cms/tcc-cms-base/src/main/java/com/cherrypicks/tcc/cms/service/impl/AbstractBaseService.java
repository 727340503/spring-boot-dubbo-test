package com.cherrypicks.tcc.cms.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dao.cache.BaseCacheDao;
import com.cherrypicks.tcc.cms.dto.BaseModel;
import com.cherrypicks.tcc.cms.service.IBaseService;


public abstract class AbstractBaseService<T extends BaseModel> implements IBaseService<T> {
	
	protected Logger log = Logger.getLogger(this.getClass().getName());
	
	protected IBaseDao<T> baseDao;

	protected BaseCacheDao<T> baseCacheDao;
	
	@PostConstruct
    public void init() {
		log.info("init");
    }

    @PreDestroy
    public void destroy() {
    	log.info("destroy");
    }
	
	public List<? extends Object> findByFilter(Map<String, Object> criteriaMap,
			String sortField, String sortType, int... args) {
		if (null == criteriaMap) {
			throw new IllegalArgumentException();
		}
		return baseDao.findByFilter(criteriaMap, sortField, sortType, args);
	}

	public T add(T object) {
		if (null == object) {
			throw new IllegalArgumentException();
		}
		object = baseDao.add(object);
		// redis add
		if (object != null && null != baseCacheDao) {
			//baseCacheDao.save(object);
			baseCacheDao.add(object);
		}
		return object;
	}

	public T update(T object) {
		if (null == object) {
			throw new IllegalArgumentException();
		}
		boolean flag = baseDao.update(object);
		// redis update
		if (flag && null != baseCacheDao) {
			//baseCacheDao.save(object);
			baseCacheDao.update(object);
		}
		return object;
	}
	
	public T updateForVersion(T object) {
		if (null == object) {
			throw new IllegalArgumentException();
		}
		object = baseDao.updateForVersion(object);
		// redis update
		if (object != null && null != baseCacheDao) {
			//baseCacheDao.save(object);
			baseCacheDao.update(object);
		}
		return object;
	}
	
	public void delete(T object) {
		if (null == object) {
			throw new IllegalArgumentException();
		}
		baseDao.del(object.getId());
		// redis del
		if ( null != baseCacheDao) {
			//baseCacheDao.del(object);
			baseCacheDao.del(object.getId());
		}
	}

	public void remove(Collection<Object> ids, String updatedBy) {
		if (null == ids) {
			throw new IllegalArgumentException();
		}
		baseDao.delByIds(ids);

		if ( null != baseCacheDao) {
			for (Object id : ids) {
				//baseCacheDao.del(id);
				baseCacheDao.del((Long) id);
			}
		}
	}
	
	public void delete(Collection<Object> ids) {
		if (null == ids) {
			throw new IllegalArgumentException();
		}
		baseDao.delByIds(ids);

		if ( null != baseCacheDao) {
			for (Object id : ids) {
				//baseCacheDao.del(id);
				baseCacheDao.del((Long) id);
			}
		}
	}
	
	public T findById(Long key) {
		if (null == key) {
			throw new IllegalArgumentException();
		}
		return baseDao.get(key);
	}

	public List<T> findAll() {
		return baseDao.findAll();
	}

	public List<T> findByIds(Collection<Object> ids) {
		if (null == ids) {
			throw new IllegalArgumentException();
		}
		return baseDao.findByIds(ids);
	}

	
	public IBaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public void setBaseCacheDao(BaseCacheDao<T> baseCacheDao) {
		this.baseCacheDao = baseCacheDao;
	}

}
