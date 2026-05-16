package org.sopt.sopkathon.global.response;

import org.sopt.sopkathon.global.exception.ErrorCode;

public record BaseResponse<T>(
        String code,
        String message,
        T data
) {
    public static <T> BaseResponse<T> success(SuccessCode successCode, T data) {
        return new BaseResponse<>(successCode.getCode(), successCode.getMessage(), data);
    }

    public static BaseResponse<Void> success(SuccessCode successCode) {
        return new BaseResponse<>(successCode.getCode(), successCode.getMessage(), null);
    }

    public static BaseResponse<Void> success(String code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    public static BaseResponse<Void> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
