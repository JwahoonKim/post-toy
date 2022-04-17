package com.flab.posttoy.service;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.commentDto.CommentRequestDto;
import com.flab.posttoy.dto.commentDto.CommentResponseDto;
import com.flab.posttoy.repository.commentRepository.CommentRepository;
import com.flab.posttoy.repository.postRepository.PostRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment addComment(Comment comment) {
        comment = saveComment(comment.getPost(), comment);
        return comment;
    }

    private Comment saveComment(Post post, Comment comment) {
        comment = commentRepository.insertComment(comment);
        post.getComments().put(comment.getId(), comment.getContent());
        return comment;
    }

    public Comment removeComment(Long postId, Long commentId) {
        Comment comment = deleteComment(postId, commentId);
        return comment;
    }

    private Comment deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.deleteComment(commentId);
        Post post = postRepository.selectPostById(postId);
        post.getComments().remove(commentId);
        return comment;
    }

    public Comment modifyComment(Long postId, Long commentId, Comment comment) {
        Comment modifiedComment = commentRepository.updateComment(commentId, comment.getContent());
        Post post = postRepository.selectPostById(postId);
        post.getComments().put(commentId, comment.getContent());
        return modifiedComment;
    }

}
