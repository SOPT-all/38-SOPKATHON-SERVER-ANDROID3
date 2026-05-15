package org.sopt.sopkathon.global.response;

import org.springframework.http.HttpStatus;

public interface SuccessCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
