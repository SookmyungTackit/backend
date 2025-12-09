package org.example.tackit.global.exception;

public class PostNotFoundException extends CustomBaseException{
    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
