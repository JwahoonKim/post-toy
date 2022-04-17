package com.flab.posttoy.controller;

import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.postDto.PostRequestDto;
import com.flab.posttoy.dto.postDto.PostDetatilResponseDto;
import com.flab.posttoy.dto.postDto.PostResponseDto;
import com.flab.posttoy.service.PostService;
import com.flab.posttoy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/posts")
    public Map<String, List<PostResponseDto>> postList() {
        List<Post> posts = postService.findAllPosts();
        List<PostResponseDto> responseDtoList = posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        return Map.of("data", responseDtoList);
    }

    @GetMapping("/posts/{postId}")
    public PostDetatilResponseDto postDetail(@PathVariable Long postId) {
//        log.info("postId = {}", postId);
        Post post = postService.findPost(postId);
        return new PostDetatilResponseDto(post);
    }

    @DeleteMapping("/posts/{postId}")
    public PostResponseDto postRemove(@PathVariable Long postId) {
        Post post = postService.removePost(postId);
        return new PostResponseDto(post);
    }

    @PostMapping("/posts")
    public PostResponseDto postAdd(@RequestBody PostRequestDto requestDto) {
//        log.info("content={}", requestDto.getContent());
//        log.info("title={}", requestDto.getTitle());
        User user = userService.findUser(requestDto.getUserId());
        Post post = requestDto.toEntity(user);
        postService.addPost(post);
        return new PostResponseDto(post);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
//        log.info("postId = {}", postId);
//        log.info("requestDto.getTitle() = {}", requestDto.getTitle());
        User user = userService.findUser(requestDto.getUserId());
        Post post = requestDto.toEntity(user);
        post.setId(postId); // 여기에 넣어야하나..?
        postService.modifyPost(postId, post);
        return new PostResponseDto(post);
    }

    @GetMapping("/users/{userId}/posts")
    public Map<String, List<PostDetatilResponseDto>> postsByUser(@PathVariable Long userId){
         var postsByUser = postService.findPostsByUser(userId)
                .stream()
                .map(PostDetatilResponseDto::new)
                .collect(Collectors.toList());
         return Map.of("data", postsByUser);
    }
}
