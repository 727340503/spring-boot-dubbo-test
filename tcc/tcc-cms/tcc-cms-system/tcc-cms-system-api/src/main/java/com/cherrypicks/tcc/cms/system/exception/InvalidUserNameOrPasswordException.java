package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class InvalidUserNameOrPasswordException extends BaseException {

	private static final long serialVersionUID = 3437225718264838305L;

	public InvalidUserNameOrPasswordException() {
		super();
	}

	public InvalidUserNameOrPasswordException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidUserNameOrPasswordException(final String message) {
		super(message);
	}

	public InvalidUserNameOrPasswordException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.INVALID_USER_NAME_OR_PASSWORD;
	}

}
