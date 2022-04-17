package com.flab.posttoy.controller;

import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.userDto.UserSaveRequestDto;
import com.flab.posttoy.dto.userDto.UserSaveResponseDto;
import com.flab.posttoy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserSaveResponseDto userAdd(@RequestBody UserSaveRequestDto requestDto) {
        User user = requestDto.toEntity();
        userService.addUser(user);
        return new UserSaveResponseDto(user);
    }
}
