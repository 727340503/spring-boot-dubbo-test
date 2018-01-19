package com.cherrypicks.tcc.cms.merchant.service;

import java.util.List;

import com.cherrypicks.tcc.cms.merchant.dto.MerchantAreaDTO;
import com.cherrypicks.tcc.cms.merchant.model.MerchantArea;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface MerchantAreaService extends IBaseService<MerchantArea>{

	List<MerchantAreaDTO> findByMerchantId(Long userId, Long merchantId, String lang);

	MerchantArea createMerchantArea(Long userId, Long merchantId, String langData, String lang);

	void updateMerchantArea(Long userId, Long merchantAreaId, String langData, String lang);

	void delByIds(Long userId, String ids, String lang);

}
