package com.mckesson.eig.roi.dao;

import com.mckesson.dm.core.common.exceptions.BaseException;
import com.mckesson.dm.core.common.exceptions.ErrorCode;

public class DaoException extends BaseException {
    private static final long serialVersionUID = 1L;

    public DaoException(ErrorCode errorCode, String message,
                        String... errorParams) {
        super(errorCode, message, errorParams);
    }

    /**
     * Constructor that accepts an initCause
     *
     * @param cause
     *            Initial cause exception
     * @param errorCode
     * @param message
     * @param errorParams
     */
    public DaoException(Throwable cause, ErrorCode errorCode, String message, String... errorParams) {
        super(cause, errorCode, message, errorParams);
    }

}