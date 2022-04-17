package com.flab.posttoy.repository.commentRepository;

import com.flab.posttoy.domain.Comment;

public interface CommentRepository {
    Comment insertComment(Comment comment);
    Comment deleteComment(Long id);
    Comment updateComment(Long id, String content);
    void clearStore();
}
