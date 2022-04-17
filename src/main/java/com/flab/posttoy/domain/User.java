package com.flab.posttoy.domain;

import com.flab.posttoy.dto.userDto.UserSaveRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class User {

    private Long id;
    private String username;
    private String password;
    private List<Post> posts = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
