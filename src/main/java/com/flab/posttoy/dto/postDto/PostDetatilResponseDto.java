package com.flab.posttoy.dto.postDto;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.domain.Post;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDetatilResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private List<String> comments = new ArrayList<>();

    public PostDetatilResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.comments = new ArrayList<>(post.getComments().values());
    }
}
