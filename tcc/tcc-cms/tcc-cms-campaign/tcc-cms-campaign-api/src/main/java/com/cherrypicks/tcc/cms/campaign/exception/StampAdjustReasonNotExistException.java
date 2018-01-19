package com.cherrypicks.tcc.cms.campaign.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class StampAdjustReasonNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public StampAdjustReasonNotExistException() {
		super();
	}

	public StampAdjustReasonNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StampAdjustReasonNotExistException(final String message) {
		super(message);
	}

	public StampAdjustReasonNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.STAMP_ADJUST_REASON_NOT_EXIST_EXCEPTION;
	}
}
