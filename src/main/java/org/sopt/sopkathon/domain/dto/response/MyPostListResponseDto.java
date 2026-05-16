package org.sopt.sopkathon.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyPostListResponseDto {
    private List<MyPostResponseDto> posts;
}