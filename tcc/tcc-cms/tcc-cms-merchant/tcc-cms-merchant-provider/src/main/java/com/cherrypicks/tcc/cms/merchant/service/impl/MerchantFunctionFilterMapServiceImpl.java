package com.cherrypicks.tcc.cms.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantFunctionFilterMapDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantFunctionFilterMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantFunctionFilterMapService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.model.SystemFunctionFilter;
import com.cherrypicks.tcc.cms.system.service.SystemFunctionFilterService;
import com.cherrypicks.tcc.util.DateUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class MerchantFunctionFilterMapServiceImpl extends AbstractBaseService<MerchantFunctionFilterMap> implements MerchantFunctionFilterMapService {
	
	@Reference(version="1.0")
	private SystemFunctionFilterService systemFunctionFilterService;
	
	@Autowired
	private MerchantFunctionFilterMapDao merchantFunctionFilterMapDao;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<MerchantFunctionFilterMap> merchantFunctionFilterMapDao) {
		super.setBaseDao(merchantFunctionFilterMapDao);
	}

	@Override
	public void addCouponManagementModule(final Long merchantId) {
		
		List<Long> systemFunctionIds = systemFunctionFilterService.findByFilterCode(SystemFunctionFilter.SystemFunctionFilerCode.COUPON_MANAGEMENT_FILTER.toValue());
		
		merchantFunctionFilterMapDao.delByMerchantIdAndSystemFuncs(merchantId,systemFunctionIds);
		
	}

	@Override
	public void removeCouponManagementModule(final Long userId,final Long merchantId) {
		
		final int couponManagementFilterCode = SystemFunctionFilter.SystemFunctionFilerCode.COUPON_MANAGEMENT_FILTER.toValue();
		
		int filterCount = merchantFunctionFilterMapDao.findByMerchantIdAndFilterCode(merchantId,couponManagementFilterCode);
		
		if(filterCount > 0){//数量不为0,则说明已经添加控制
			return;
		}
		
		List<Long> systemFunctionIds = systemFunctionFilterService.findByFilterCode(couponManagementFilterCode);
		List<MerchantFunctionFilterMap> merchantFuncFilterMaps = new ArrayList<MerchantFunctionFilterMap>();
		
		for(final Long systemFuncId : systemFunctionIds){
			MerchantFunctionFilterMap merchantFuncFilterMap = new MerchantFunctionFilterMap();
			merchantFuncFilterMap.setFuncId(systemFuncId);
			merchantFuncFilterMap.setMerchantId(merchantId);
			merchantFuncFilterMap.setCreatedBy(String.valueOf(userId));
			merchantFuncFilterMap.setCreatedTime(DateUtil.getCurrentDate());
			merchantFuncFilterMap.setIsDeleted(false);
			
			merchantFuncFilterMaps.add(merchantFuncFilterMap);
		}
		
		merchantFunctionFilterMapDao.batchAdd(merchantFuncFilterMaps);
	}
	
	
}
