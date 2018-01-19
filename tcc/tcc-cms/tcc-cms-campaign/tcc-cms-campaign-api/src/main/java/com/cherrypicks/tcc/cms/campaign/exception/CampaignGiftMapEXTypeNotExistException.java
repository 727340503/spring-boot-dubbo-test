package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CampaignGiftMapEXTypeNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CampaignGiftMapEXTypeNotExistException() {
		super();
	}

	public CampaignGiftMapEXTypeNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CampaignGiftMapEXTypeNotExistException(final String message) {
		super(message);
	}

	public CampaignGiftMapEXTypeNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.CAMPAIGN_GIFT_MAP_EX_TYPE_NOT_EXIST;
	}
}
