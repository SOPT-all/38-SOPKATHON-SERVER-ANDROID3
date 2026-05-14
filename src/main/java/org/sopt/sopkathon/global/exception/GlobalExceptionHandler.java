package org.sopt.sopkathon.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.sopt.sopkathon.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("[BusinessException] {}", e.getMessage());
        return errorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("[MethodArgumentNotValidException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse<Void>> handleBindException(BindException e) {
        log.warn("[BindException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("[MissingServletRequestParameterException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.MISSING_PARAMETER);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("[MethodArgumentTypeMismatchException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.INVALID_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("[HttpMessageNotReadableException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleNoHandlerFound(NoHandlerFoundException e) {
        log.warn("[NoHandlerFoundException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("[HttpRequestMethodNotSupportedException] {}", e.getMessage());
        return errorResponse(CommonErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
        log.error("[Exception] {}", e.getMessage(), e);
        return errorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<BaseResponse<Void>> errorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.error(errorCode));
    }
}
