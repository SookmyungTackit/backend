package org.example.tackit.global.exception;

public class PostInactiveException extends CustomBaseException{
    public PostInactiveException(ErrorCode errorCode) {
        super(errorCode);
    }
}
