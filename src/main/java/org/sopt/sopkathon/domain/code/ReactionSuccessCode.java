package org.sopt.sopkathon.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.global.response.SuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReactionSuccessCode implements SuccessCode {

    CREATE_REACTION_SUCCESS(
            HttpStatus.OK,
            "REACTION_200",
            "리액션이 성공적으로 전송되었습니다."
    );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}