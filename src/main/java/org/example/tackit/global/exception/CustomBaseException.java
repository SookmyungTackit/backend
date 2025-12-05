package org.example.tackit.global.exception;

public class CustomBaseException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
