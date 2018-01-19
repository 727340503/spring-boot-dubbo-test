package com.cherrypicks.tcc.cms.merchant.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class MerchantAreaNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public MerchantAreaNotExistException() {
		super();
	}

	public MerchantAreaNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MerchantAreaNotExistException(final String message) {
		super(message);
	}

	public MerchantAreaNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.MERCHANT_AREA_NOT_EXIST;
	}
}
