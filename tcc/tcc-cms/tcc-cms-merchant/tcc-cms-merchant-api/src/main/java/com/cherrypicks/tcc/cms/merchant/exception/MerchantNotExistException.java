package com.cherrypicks.tcc.cms.merchant.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class MerchantNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public MerchantNotExistException() {
		super();
	}

	public MerchantNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MerchantNotExistException(final String message) {
		super(message);
	}

	public MerchantNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.MERCHANT_NOT_EXIST;
	}
}
