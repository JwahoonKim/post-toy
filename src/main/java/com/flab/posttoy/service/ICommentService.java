package com.flab.posttoy.service;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.dto.CommentDTO;
import com.flab.posttoy.dto.UpdateCommentDTO;

public interface ICommentService {
    CommentDTO addComment(CommentDTO commentDTO);

    // 비지니스 로직을 가지는 부분은 DTO 와 의존성이 없어야 한다
    Comment modifyComment(String content, Long id);

    void removeComment(Long id);
}
