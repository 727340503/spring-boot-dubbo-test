package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CampaignIsOverOrExpiredException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CampaignIsOverOrExpiredException() {
		super();
	}

	public CampaignIsOverOrExpiredException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CampaignIsOverOrExpiredException(final String message) {
		super(message);
	}

	public CampaignIsOverOrExpiredException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.CAMPAIGN_IS_OVER;
	}
}
