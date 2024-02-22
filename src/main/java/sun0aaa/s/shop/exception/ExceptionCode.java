package sun0aaa.s.shop.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404,"Member Not Found"),
    UNABLE_TO_SEND_EMAIL(500,"Unable To Send Email")
    ;
    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message){
        this.status=status;
        this.message=message;
    }
}
