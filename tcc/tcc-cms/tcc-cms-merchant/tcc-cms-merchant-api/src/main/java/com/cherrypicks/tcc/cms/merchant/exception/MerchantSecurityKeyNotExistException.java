package com.cherrypicks.tcc.cms.merchant.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class MerchantSecurityKeyNotExistException extends BaseException {

	private static final long serialVersionUID = -1775446528078230845L;

	public MerchantSecurityKeyNotExistException() {
		super();
	}

	public MerchantSecurityKeyNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MerchantSecurityKeyNotExistException(final String message) {
		super(message);
	}

	public MerchantSecurityKeyNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.MERCHANT_SECURITY_KEY_NOT_EXIST;
	}
}
