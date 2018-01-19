package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CampaignGiftMapNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CampaignGiftMapNotExistException() {
		super();
	}

	public CampaignGiftMapNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CampaignGiftMapNotExistException(final String message) {
		super(message);
	}

	public CampaignGiftMapNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.CAMPAIGN_GIFT_MAP_NOT_EXIST;
	}
}
