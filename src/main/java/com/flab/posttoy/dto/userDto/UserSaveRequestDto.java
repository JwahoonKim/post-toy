package com.flab.posttoy.dto.userDto;

import com.flab.posttoy.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequestDto {
    private String username;
    private String password;

    public User toEntity() {
        return new User(username, password);
    }
}
