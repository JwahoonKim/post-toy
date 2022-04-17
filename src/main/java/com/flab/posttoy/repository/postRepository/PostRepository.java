package com.flab.posttoy.repository.postRepository;

import com.flab.posttoy.domain.Post;

import java.util.List;

public interface PostRepository {
    Post insertPost(Post post);
    Post deletePost(Long id);
    Post updatePost(Long id, String title, String content);
    Post selectPostById(Long id);
    List<Post> selectAllPosts();
    List<Post> selectAllPostsByUser(Long id);
    void clearStore();
}
