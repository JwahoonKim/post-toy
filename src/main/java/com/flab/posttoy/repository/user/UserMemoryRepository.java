package com.flab.posttoy.repository.user;

import com.flab.posttoy.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserMemoryRepository implements UserRepository {

    // HashMap? ConcurrentHashMap??
    private final ConcurrentHashMap<Long, User> store = new ConcurrentHashMap<>();

    // 동시성 문제를 고려해 AtomicLong 사용
    private AtomicLong sequence = new AtomicLong();

    @Override
    public User save(User user) {
        user.setId(sequence.incrementAndGet());
        user.setCreatedAt(createdTime());
        user.setModifiedAt(user.getCreatedAt());
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByName(String username) {
        return store.values().stream()
                .filter(user->user.getUsername().equals(username))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }


}
