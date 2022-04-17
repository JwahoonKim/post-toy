package com.flab.posttoy.dto.userDto;

import com.flab.posttoy.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveResponseDto {
    private Long id;
    private String username;

    public UserSaveResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
