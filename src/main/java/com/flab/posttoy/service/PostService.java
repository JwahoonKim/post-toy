package com.flab.posttoy.service;

import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.postDto.PostRequestDto;
import com.flab.posttoy.dto.postDto.PostDetatilResponseDto;
import com.flab.posttoy.dto.postDto.PostResponseDto;
import com.flab.posttoy.repository.postRepository.PostRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<Post> findAllPosts() {
        List<Post> posts = postRepository.selectAllPosts();
        return posts;
    }

    public Post findPost(Long postId) {
        return postRepository.selectPostById(postId);
    }

    public Post removePost(Long postId) {
        Post deletedPost = postRepository.deletePost(postId);
        return deletedPost;
    }

    public Post addPost(Post post) {
        postRepository.insertPost(post);
        log.info("post content ={}", post.getContent());
        return post;
    }

    public Post modifyPost(Long postId, Post post) {
        String newTitle = post.getTitle();
        String newContent = post.getContent();
        Post updatedPost = postRepository.updatePost(postId, newTitle, newContent);
        return updatedPost;
    }

    public List<Post> findPostsByUser(Long userId) {
        return postRepository.selectAllPostsByUser(userId);
    }
}
