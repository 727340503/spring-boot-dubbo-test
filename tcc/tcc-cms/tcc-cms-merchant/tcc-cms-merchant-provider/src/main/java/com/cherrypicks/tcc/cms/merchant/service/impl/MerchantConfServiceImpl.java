package com.cherrypicks.tcc.cms.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dao.jedis.RedisKey;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantConfigDao;
import com.cherrypicks.tcc.cms.merchant.dao.cache.MerchantConfigCacheDao;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantConfigNotExistException;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantNotExistException;
import com.cherrypicks.tcc.cms.merchant.model.Merchant;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.util.DateUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class MerchantConfServiceImpl extends AbstractBaseService<MerchantConfig> implements MerchantConfigService {

	@Autowired
	private MerchantConfigCacheDao merchantConfigCacheDao;

	@Autowired
	private MerchantConfigDao merchantConfigDao;
	
	@Autowired
	private MerchantService merchantService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<MerchantConfig> merchantConfigDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(merchantConfigDao);
	}

	@Override
	public MerchantConfig findByMerchantId(final Long merchantId) {

		// find by redis
		MerchantConfig merchantConfig = merchantConfigCacheDao.get(RedisKey.getMerchantConfigKey(merchantId));

		// check is null
		if (null == merchantConfig) {

			// find by DB
			merchantConfig = merchantConfigDao.findByMerchantId(merchantId);

			// check not null
			if (null != merchantConfig) {

				// save to redis
				merchantConfigCacheDao.add(RedisKey.getMerchantConfigKey(merchantId), merchantConfig);
			}

		}
		
		if(null == merchantConfig){
			throw new MerchantConfigNotExistException();
		}

		return merchantConfig;
	}

	@Override
	@Transactional
	public void updateMerchantConfig(final MerchantConfig merchantConfig) {
		
		final MerchantConfig checkMerchantConfig = merchantConfigDao.get(merchantConfig.getId());
		if(null == checkMerchantConfig){
			return;
		}
		
		merchantConfig.setMerchantId(checkMerchantConfig.getMerchantId());
		
		merchantConfig.setCreatedBy(checkMerchantConfig.getCreatedBy());
		merchantConfig.setCreatedTime(checkMerchantConfig.getCreatedTime());
		merchantConfig.setIsDeleted(checkMerchantConfig.getIsDeleted());
		merchantConfig.setUpdatedBy("CMS_ADMIN");
		merchantConfig.setUpdatedTime(checkMerchantConfig.getUpdatedTime());
		
		final MerchantConfig updateResult = merchantConfigDao.updateForVersion(merchantConfig);
		
		// del by redis
		merchantConfigCacheDao.del(RedisKey.getMerchantConfigKey(updateResult.getMerchantId()));
		// save to redis
		merchantConfigCacheDao.add(RedisKey.getMerchantConfigKey(updateResult.getMerchantId()), updateResult);
	}

	@Override
	@Transactional
	public MerchantConfig createMerchantConfig(final MerchantConfig merchantConfig, final String lang) {
		
		final MerchantConfig checkMerchantConfig = merchantConfigDao.findByMerchantId(merchantConfig.getMerchantId());
		if(null != checkMerchantConfig){
			return null;
		}
		
		Merchant merchant = merchantService.findById(merchantConfig.getMerchantId());
		if(null == merchant){
			throw new MerchantNotExistException();
		}
		
		merchantConfig.setCreatedBy("CMS_ADMIN");
		merchantConfig.setCreatedTime(DateUtil.getCurrentDate());
		merchantConfig.setIsDeleted(false);
		merchantConfig.setId(null);
		
		final MerchantConfig result = merchantConfigDao.add(merchantConfig);
		
		merchantConfigCacheDao.add(RedisKey.getMerchantConfigKey(merchantConfig.getMerchantId()),result);
		
		return result;
	}

	@Override
	public void updateMerchantConfigCache(final Long merchantId) {
		// del by redis
		merchantConfigCacheDao.del(RedisKey.getMerchantConfigKey(merchantId));
		
		//find for DB
		MerchantConfig merchantConfig = merchantConfigDao.findByMerchantId(merchantId);
		
		if(null != merchantConfig){
			// save to redis
			merchantConfigCacheDao.add(RedisKey.getMerchantConfigKey(merchantId), merchantConfig);
		}
	}

}