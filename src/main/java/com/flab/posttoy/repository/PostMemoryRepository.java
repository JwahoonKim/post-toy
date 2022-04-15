package com.flab.posttoy.repository;

import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostMemoryRepository implements MyRepository<Long, Post>{

    // HashMap? ConcurrentHashMap??
    private final ConcurrentHashMap<Long, Post> store = new ConcurrentHashMap<>();

    // 동시성 문제를 고려해 AtomicLong 사용
    private AtomicLong sequence = new AtomicLong();

    @Override
    public Post save(Post post) {
        post.setId(sequence.incrementAndGet());
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Post> findByName(String title) {
        return store.values().stream()
                .filter(post -> post.getTitle().equals(title))
                .findAny();
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }
}