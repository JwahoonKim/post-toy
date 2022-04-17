package com.flab.posttoy.repository.userRepository;

import com.flab.posttoy.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<Long, User> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public User insertUser(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public User selectUserById(Long id) {
        return store.get(id);
    }

    public void clearStore() {
        store.clear();
    }
}
