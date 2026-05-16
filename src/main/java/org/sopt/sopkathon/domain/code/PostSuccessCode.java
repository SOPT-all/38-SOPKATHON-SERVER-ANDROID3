package org.sopt.sopkathon.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.global.response.SuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostSuccessCode implements SuccessCode {

    CREATE_POST(HttpStatus.CREATED, "POST_201", "고민 작성에 성공했습니다."),
    GET_POSTS(HttpStatus.OK, "POST_200", "카드 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
