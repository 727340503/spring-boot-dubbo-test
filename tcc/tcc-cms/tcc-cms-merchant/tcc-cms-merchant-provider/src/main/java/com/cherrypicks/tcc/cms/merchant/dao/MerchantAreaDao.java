package com.cherrypicks.tcc.cms.merchant.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.dto.MerchantAreaDTO;
import com.cherrypicks.tcc.cms.merchant.model.MerchantArea;

public interface MerchantAreaDao extends IBaseDao<MerchantArea> {

	List<MerchantAreaDTO> findByMerchantId(Long merchantId);


}
