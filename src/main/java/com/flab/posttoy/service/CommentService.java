package com.flab.posttoy.service;

import com.flab.posttoy.dto.CommentDTO;
import com.flab.posttoy.dto.UpdateCommentDTO;

public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO);

    CommentDTO modifyComment(UpdateCommentDTO updateCommentDTO, Long id);

    void removeComment(Long id);
}
