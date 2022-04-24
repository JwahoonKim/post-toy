package com.flab.posttoy.web.dto.mapper;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.dto.CommentDTO;
import com.flab.posttoy.dto.UpdateCommentDTO;
import com.flab.posttoy.web.dto.request.RequestCommentDTO;
import com.flab.posttoy.web.dto.response.ResponseCommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebCommentMapper {
    CommentDTO toCommentDto(RequestCommentDTO requestCommentDTO);
    UpdateCommentDTO toUpdateCommentDto(RequestCommentDTO requestCommentDTO);

    ResponseCommentDTO toResponseCommentDto(Comment commentDTO);
}
