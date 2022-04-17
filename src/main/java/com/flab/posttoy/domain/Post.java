package com.flab.posttoy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class Post {

    private Long id;
    private String title;
    private String content;
    private User user;
    private Map<Long, String> comments = new HashMap<>();

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

}
