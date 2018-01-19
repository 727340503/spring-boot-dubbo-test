package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CampaignNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CampaignNotExistException() {
		super();
	}

	public CampaignNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CampaignNotExistException(final String message) {
		super(message);
	}

	public CampaignNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.CAMPAIGN_NOT_EXIST;
	}
}
