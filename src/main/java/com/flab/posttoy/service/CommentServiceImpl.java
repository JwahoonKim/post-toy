package com.flab.posttoy.service;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.dto.CommentDTO;
import com.flab.posttoy.dto.UpdateCommentDTO;
import com.flab.posttoy.dto.mapper.CommentMapper;
import com.flab.posttoy.exception.comment.CommentNotFoundException;
import com.flab.posttoy.exception.post.PostNotFoundException;
import com.flab.posttoy.repository.comment.CommentRepository;
import com.flab.posttoy.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        validateExistPost(commentDTO.getPostId());
        Comment comment = commentRepository.insert(commentMapper.toComment(commentDTO));
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDTO modifyComment(UpdateCommentDTO updateCommentDTO, Long id) {
        Comment existComment = validateExistComment(id);
        existComment.changeComment(updateCommentDTO.getContent());
        commentRepository.update(existComment);
        return commentMapper.toCommentDto(existComment);
    }

    @Override
    public void removeComment(Long id) {
        Comment existComment = validateExistComment(id);
        commentRepository.delete(existComment.getId());
    }

    private Comment validateExistComment(Long id) {
        return commentRepository.selectById(id).orElseThrow(() ->
                new CommentNotFoundException(id + ": 해당하는 댓글이 존재하지 않습니다."));
    }

    private void validateExistPost(Long postId) {
        postRepository.selectById(postId).orElseThrow(()->
                new PostNotFoundException(postId + ":해당하는 포스트가 존재하지 않습니다"));
    }
}
