package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class SystemRoleNotExistException extends BaseException {

	private static final long serialVersionUID = 4090115483059651856L;

	public SystemRoleNotExistException() {
		super();
	}

	public SystemRoleNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SystemRoleNotExistException(final String message) {
		super(message);
	}

	public SystemRoleNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.SYSTEM_ROLE_NOT_EXIST;
	}
}
