package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class InvalidArgumentException extends BaseException {

    private static final long serialVersionUID = 3437225718264838305L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(final String message) {
        super(message);
    }

    public InvalidArgumentException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.ILLEGAL_ARGUMENT;
    }

}
