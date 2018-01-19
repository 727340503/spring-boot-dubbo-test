package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

public class RecordVersionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4456871530489376722L;
	private static int errorCode = CmsCodeStatus.RECORD_VERSION_EXCEPTION;
	
	public RecordVersionException() {
		super();
	}

	public RecordVersionException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordVersionException(String langCode) {
		super(I18nUtil.getMessage(errorCode, null, langCode));
	}

	public RecordVersionException(Throwable cause) {
		super(cause);
	}

	public int getErrorCode() {
		return errorCode;
	}
}
