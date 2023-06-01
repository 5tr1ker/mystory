package com.team.mystory.common;

import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMessage<T> {
    private String code;
    private String message;
    private T data;

    private ResponseMessage(String code , String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseMessage(ResponseCode responseCode , T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public static ResponseMessage of(ResponseCode responseCode) {
        return new ResponseMessage(responseCode.getCode() , responseCode.getMessage());
    }

    public static ResponseMessage of(ResponseCode responseCode , String message) {
        return new ResponseMessage(responseCode.getCode() , message);
    }

    public static <T> ResponseMessage of(ResponseCode responseCode , T data) {
        return new ResponseMessage(responseCode , data);
    }

}
