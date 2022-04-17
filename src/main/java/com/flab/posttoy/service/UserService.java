package com.flab.posttoy.service;

import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.userDto.UserSaveRequestDto;
import com.flab.posttoy.dto.userDto.UserSaveResponseDto;
import com.flab.posttoy.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addUser(User user) {
        user = userRepository.insertUser(user);
        return user;
    }

    public User findUser(Long id) {
        return userRepository.selectUserById(id);
    }
}
