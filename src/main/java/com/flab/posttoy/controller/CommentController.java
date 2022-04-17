package com.flab.posttoy.controller;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.commentDto.CommentRequestDto;
import com.flab.posttoy.dto.commentDto.CommentResponseDto;
import com.flab.posttoy.service.CommentService;
import com.flab.posttoy.service.PostService;
import com.flab.posttoy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponseDto commentAdd(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        log.info("userId = {}", requestDto.getUserId());
        Comment comment = toCommentEntity(postId, requestDto);
        commentService.addComment(comment);
        return new CommentResponseDto(comment);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponseDto commentRemove(@PathVariable Long postId,
                                            @PathVariable Long commentId
//                                            @RequestBody CommentRequestDto requestDto
    )
    {
        Comment comment = commentService.removeComment(postId, commentId);
        return new CommentResponseDto(comment);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponseDto commentModify(@PathVariable Long postId,
                                            @PathVariable Long commentId,
                                            @RequestBody CommentRequestDto requestDto) {
        Comment comment = toCommentEntity(postId, requestDto);
        comment.setId(commentId);
        commentService.modifyComment(postId, commentId, comment);
        return new CommentResponseDto(comment);
    }

    private Comment toCommentEntity(Long postId, CommentRequestDto requestDto) {
        User user = userService.findUser(requestDto.getUserId());
        Post post = postService.findPost(postId);
        return new Comment(requestDto.getContent(), user, post);
    }
}
