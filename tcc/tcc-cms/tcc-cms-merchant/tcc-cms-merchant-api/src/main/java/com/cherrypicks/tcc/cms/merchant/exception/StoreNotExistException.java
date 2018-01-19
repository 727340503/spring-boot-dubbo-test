package com.cherrypicks.tcc.cms.merchant.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class StoreNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public StoreNotExistException() {
		super();
	}

	public StoreNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StoreNotExistException(final String message) {
		super(message);
	}

	public StoreNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.STORE_NOT_EXIST;
	}
}
