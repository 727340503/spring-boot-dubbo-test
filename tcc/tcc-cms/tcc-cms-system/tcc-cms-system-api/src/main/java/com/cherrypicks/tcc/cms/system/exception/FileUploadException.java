package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class FileUploadException extends BaseException {

	private static final long serialVersionUID = -656836584028399036L;

	public FileUploadException() {
		super();
	}

	public FileUploadException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FileUploadException(final String message) {
		super(message);
	}

	public FileUploadException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.FILE_UPLOAD_EXCEPTION;
	}
}
