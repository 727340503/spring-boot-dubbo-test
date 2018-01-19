package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class GiftNotExistException extends BaseException {
	
	private static final long serialVersionUID = 7459625428853615105L;

	public GiftNotExistException() {
		super();
	}

	public GiftNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GiftNotExistException(final String message) {
		super(message);
	}

	public GiftNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.GIFT_NOT_EXIST;
	}
}
