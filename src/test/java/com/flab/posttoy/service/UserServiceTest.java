package com.flab.posttoy.service;

import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.userDto.UserSaveRequestDto;
import com.flab.posttoy.dto.userDto.UserSaveResponseDto;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserRepository userRepository = new MemoryUserRepository();
    UserService userService = new UserService(userRepository);

    @AfterEach
    void afterEach() {
        userRepository.clearStore();
    }

    @Test
    public void 유저_등록_테스트() {
        //given
        User user = new User("user1", "1234");

        //when
        userService.addUser(user);

        //then
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("user1");
    }

}