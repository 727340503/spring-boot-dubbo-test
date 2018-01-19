package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class HaveNoPermissionException extends BaseException {

    private static final long serialVersionUID = 5046492633494815871L;

    public HaveNoPermissionException() {
        super();
    }

    public HaveNoPermissionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HaveNoPermissionException(final String message) {
        super(message);
    }

    public HaveNoPermissionException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.NO_PERMISSION;
    }
}
