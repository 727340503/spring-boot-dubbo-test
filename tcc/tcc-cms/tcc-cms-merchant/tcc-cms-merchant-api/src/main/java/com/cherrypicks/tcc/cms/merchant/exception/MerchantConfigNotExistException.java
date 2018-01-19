package com.cherrypicks.tcc.cms.merchant.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class MerchantConfigNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public MerchantConfigNotExistException() {
		super();
	}

	public MerchantConfigNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MerchantConfigNotExistException(final String message) {
		super(message);
	}

	public MerchantConfigNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.MERCHANT_CONFIG_NOT_EXIST;
	}
}
