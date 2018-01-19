package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class JsonParseException extends BaseException {

	private static final long serialVersionUID = -7304814643956383191L;

	public JsonParseException() {
		super();
	}

	public JsonParseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public JsonParseException(final String message) {
		super(message);
	}

	public JsonParseException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.JSON_PARSE_EXCEPTION;
	}
}
