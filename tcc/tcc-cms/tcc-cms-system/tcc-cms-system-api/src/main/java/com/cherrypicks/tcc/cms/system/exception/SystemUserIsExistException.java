package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class SystemUserIsExistException extends BaseException {

	private static final long serialVersionUID = 4090115483059651856L;

	public SystemUserIsExistException() {
		super();
	}

	public SystemUserIsExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SystemUserIsExistException(final String message) {
		super(message);
	}

	public SystemUserIsExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.SYSTEM_USER_IS_EXIST;
	}
}
