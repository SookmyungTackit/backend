package org.example.tackit.global.exception;

public class AccessDeniedCustomException extends CustomBaseException{
    public AccessDeniedCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
