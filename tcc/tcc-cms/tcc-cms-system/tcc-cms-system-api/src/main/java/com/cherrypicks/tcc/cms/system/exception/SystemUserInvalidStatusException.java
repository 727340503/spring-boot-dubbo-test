package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class SystemUserInvalidStatusException extends BaseException {

	private static final long serialVersionUID = 3437225718264838305L;

	public SystemUserInvalidStatusException() {
		super();
	}

	public SystemUserInvalidStatusException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SystemUserInvalidStatusException(final String message) {
		super(message);
	}

	public SystemUserInvalidStatusException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.INVALID_USER_STATUS;
	}

}
