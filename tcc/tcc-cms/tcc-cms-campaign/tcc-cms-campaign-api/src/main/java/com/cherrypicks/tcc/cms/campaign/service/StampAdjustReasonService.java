package com.cherrypicks.tcc.cms.campaign.service;

import com.cherrypicks.tcc.cms.campaign.dto.StampAdjustReasonDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.StampAdjustReason;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface StampAdjustReasonService extends IBaseService<StampAdjustReason>{

	StampAdjustReason createStampAdjustReason(final Long userId, final Long merchantId, final String langData, final String lang);

	void updateStampAdjustReason(final Long userId, final Long id, final String langData, final String lang);

	void deleteStampAdjustReason(final Long userId, final String ids, final String lang);

	StampAdjustReasonDetailDTO getStampAdjustReasonDetail(final Long userId, final Long id, final String lang);


}
