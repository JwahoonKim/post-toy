package com.flab.posttoy.web.controller;

import com.flab.posttoy.dto.CommentDTO;
import com.flab.posttoy.service.CommentService;
import com.flab.posttoy.web.dto.mapper.WebCommentMapper;
import com.flab.posttoy.web.dto.request.RequestCommentDTO;
import com.flab.posttoy.web.dto.response.ResponseCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final WebCommentMapper commentMapper;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseCommentDTO> commentSave(@RequestBody @Valid RequestCommentDTO requestCommentDTO){
        CommentDTO commentDTO = commentService.addComment(commentMapper.toCommentDto(requestCommentDTO));
        ResponseCommentDTO responseCommentDTO = commentMapper.toResponseCommentDto(commentDTO);
        return ResponseEntity.created(URI.create("/posts/"+responseCommentDTO.getPostId())).body(responseCommentDTO);
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ResponseCommentDTO> commentModify(@RequestBody RequestCommentDTO requestCommentDTO, @PathVariable Long commentId) {
        CommentDTO commentDTO = commentService.modifyComment(commentMapper.toUpdateCommentDto(requestCommentDTO), commentId);
        ResponseCommentDTO responseCommentDTO = commentMapper.toResponseCommentDto(commentDTO);
        return new ResponseEntity<>(responseCommentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> commentRemove(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }
}
