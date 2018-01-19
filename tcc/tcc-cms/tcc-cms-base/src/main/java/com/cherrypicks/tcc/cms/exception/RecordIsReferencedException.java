package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class RecordIsReferencedException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public RecordIsReferencedException() {
		super();
	}

	public RecordIsReferencedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RecordIsReferencedException(final String message) {
		super(message);
	}

	public RecordIsReferencedException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.RECORD_IS_REFERENCED;
	}
}
