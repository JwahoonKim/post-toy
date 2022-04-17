package com.flab.posttoy.repository.userRepository;

import com.flab.posttoy.domain.User;

public interface UserRepository {
    User insertUser(User user);
    User selectUserById(Long id);
    void clearStore();
}
