package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class SystemRoleIsExistException extends BaseException {

	private static final long serialVersionUID = 4090115483059651856L;

	public SystemRoleIsExistException() {
		super();
	}

	public SystemRoleIsExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SystemRoleIsExistException(final String message) {
		super(message);
	}

	public SystemRoleIsExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.SYSTEM_ROLE_IS_EXIST;
	}
}
