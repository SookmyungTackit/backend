package org.example.tackit.global.exception;

public class MemberNotFoundException extends CustomBaseException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
