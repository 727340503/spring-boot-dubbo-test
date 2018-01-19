package com.cherrypicks.tcc.cms.exception;

public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 7374794658111025280L;

    /**
     * MessageFormat arguments
     */
    private Object[] args;

    private Object data = "";

    protected BaseException(final Object... args) {
        super();
        this.args = args;
    }

    protected BaseException(final String message, final Throwable cause, final Object... args) {
        super(message, cause);
        this.args = args;
    }

    protected BaseException(final String message, final Object... args) {
        super(message);
        this.args = args;
    }

    protected BaseException(final Throwable cause, final Object... args) {
        super(cause);
        this.args = args;
    }

    public abstract int getErrorCode();

    public Object[] getArgs() {
        if (this.args == null) {
            return null;
        } else {
            return this.args.clone();
        }
    }

    public void setArgs(final Object[] args) {
        if (args == null) {
            this.args = null;
        } else {
            this.args = args.clone();
        }
    }

    public Object getData() {
        return data;
    }

    public BaseException setData(final Object errorData) {
        this.data = errorData;
        return this;
    }
}
