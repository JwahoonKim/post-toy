package com.flab.posttoy.dto.commentDto;

import com.flab.posttoy.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor // 테스트코드를 위한 생성자인데.. 이게 맞나?
public class CommentRequestDto {
    private Long userId;
    private String content;

}
