package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class StampNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public StampNotExistException() {
		super();
	}

	public StampNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StampNotExistException(final String message) {
		super(message);
	}

	public StampNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.STAMP_NOT_EXIST;
	}
}
