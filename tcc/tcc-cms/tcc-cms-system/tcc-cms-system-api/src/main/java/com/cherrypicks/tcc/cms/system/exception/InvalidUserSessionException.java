package com.cherrypicks.tcc.cms.system.exception;

import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.util.CmsCodeStatus;

public class InvalidUserSessionException extends BaseException {

    private static final long serialVersionUID = 2470501396046046890L;

    public InvalidUserSessionException() {
        super(new Object[0]);
    }

    public InvalidUserSessionException(final String message, final Throwable cause) {
        super(message, cause, new Object[0]);
    }

    public InvalidUserSessionException(final String message) {
        super(message, new Object[0]);
    }

    public InvalidUserSessionException(final Throwable cause) {
        super(cause, new Object[0]);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.ILLEGAL_SESSION;
    }
}
