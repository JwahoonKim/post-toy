package com.flab.posttoy.repository.commentRepository;

import com.flab.posttoy.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemoryCommentRepository implements CommentRepository {

    private final Map<Long, Comment> store = new HashMap<>();
    private Long sequence = 0L;

    public Comment selectCommentById(Long commentId) {
        return store.get(commentId);
    }

    public Comment insertComment(Comment comment) {
        comment.setId(++sequence);
        store.put(comment.getId(), comment);
        return comment;
    }

    public Comment deleteComment(Long commentId) {
        return store.remove(commentId);
    }

    public Comment updateComment(Long commentId, String content) {
        Comment comment = selectCommentById(commentId);
        comment.setContent(content);
        return comment;
    }

    public void clearStore() {
        store.clear();
    }

}
