package com.flab.posttoy.repository.postRepository;

import com.flab.posttoy.domain.Post;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Repository
@Slf4j
public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Post insertPost(Post post) {
        post.setId(++sequence);
        log.info("post = {}", post);
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Post deletePost(Long id) {
        return store.remove(id);
    }

    @Override
    public Post updatePost(Long id, String title, String content) {
        Post updatePost = store.get(id);
        updatePost.setTitle(title);
        updatePost.setContent(content);
        return updatePost;
    }

    @Override
    public Post selectPostById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Post> selectAllPosts() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Post> selectAllPostsByUser(Long userId) {
        List<Post> posts = new ArrayList<>(store.values());
        return posts.stream()
                .filter(post -> post.getUser().getId() == (userId))
                .collect(Collectors.toList());
    }

    public void clearStore() {
        store.clear();
    }
}
