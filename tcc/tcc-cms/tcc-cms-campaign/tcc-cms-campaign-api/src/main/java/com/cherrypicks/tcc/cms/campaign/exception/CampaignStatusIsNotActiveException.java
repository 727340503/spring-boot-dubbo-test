package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CampaignStatusIsNotActiveException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CampaignStatusIsNotActiveException() {
		super();
	}

	public CampaignStatusIsNotActiveException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CampaignStatusIsNotActiveException(final String message) {
		super(message);
	}

	public CampaignStatusIsNotActiveException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.CAMPAIGN_STATUS_IS_NOT_ACTIVE_EXCEPTION;
	}
}
