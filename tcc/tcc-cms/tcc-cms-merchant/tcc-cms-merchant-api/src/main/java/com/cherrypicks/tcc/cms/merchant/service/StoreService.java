package com.cherrypicks.tcc.cms.merchant.service;

import java.util.List;

import com.cherrypicks.tcc.cms.merchant.dto.StoreDetailDTO;
import com.cherrypicks.tcc.cms.merchant.model.Store;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface StoreService extends IBaseService<Store>{

	StoreDetailDTO findDetailById(final Long userId, final Long storeId, final String lang);

	Store createStore(Long userId, String externalStoreId, Long merchantId,Long merchantAreaId, String phone, Integer status,final String lat, final String lng, String langData,String lang);

	void updateStore(Long userId, Long storeId, Long merchantAreaId,String externalStoreId, String phone,Integer status, final String lat, final String lng, String langData, String lang);

	long findCountByMerchantAreaIds(List<Object> merchantAreaIds);

}
