package com.flab.posttoy.repository.userRepository;

import com.flab.posttoy.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @Test
    void 회원_등록_테스트() {
        //given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");

        //when
        User saveUser = memoryUserRepository.insertUser(user1);

        //then
        User findUser = memoryUserRepository.selectUserById(saveUser.getId());
        assertThat(user1).isEqualTo(findUser);
        assertThat(user1.getId()).isEqualTo(1);
        assertThat(user1.getUsername()).isEqualTo("user1");
    }
}