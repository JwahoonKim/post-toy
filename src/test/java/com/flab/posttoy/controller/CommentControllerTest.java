package com.flab.posttoy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.commentDto.CommentRequestDto;
import com.flab.posttoy.dto.postDto.PostRequestDto;
import com.flab.posttoy.repository.commentRepository.CommentRepository;
import com.flab.posttoy.repository.postRepository.MemoryPostRepository;
import com.flab.posttoy.repository.postRepository.PostRepository;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
import com.flab.posttoy.service.CommentService;
import com.flab.posttoy.service.PostService;
import com.flab.posttoy.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserService userService;
    @Autowired PostService postService;
    @Autowired CommentService commentService;
    @Autowired UserRepository userRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;

    // afterEach 처리 어케하지..

    @Test
    public void 댓글_등록_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        Post post = new Post("title1", "content1", user);
        postService.addPost(post);
        CommentRequestDto commentRequestDto = new CommentRequestDto(user.getId(), "comment1");
        String content = objectMapper.writeValueAsString(commentRequestDto);

        //when
        ResultActions response = mvc.perform(post("/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("content").value("comment1"));
    }

    @Test
    public void 댓글_삭제_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        Post post = new Post("title1", "content1", user);
        postService.addPost(post);
        Comment comment1 = new Comment("comment1", user, post);
        Comment comment2 = new Comment("comment2", user, post);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        //when
        ResultActions response = mvc.perform(delete("/posts/1/comments/2")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("content").value("comment2"));
    }

    @Test
    public void 댓글_수정_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        Post post = new Post("title1", "content1", user);
        postService.addPost(post);
        Comment comment1 = new Comment("comment1", user, post);
        Comment comment2 = new Comment("comment2", user, post);
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        CommentRequestDto commentRequestDto = new CommentRequestDto(user.getId(), "new comment");
        String content = objectMapper.writeValueAsString(commentRequestDto);

        //when
        ResultActions response = mvc.perform(put("/posts/1/comments/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("content").value("new comment"));
    }

    private User makeUser(String username) {
        return new User(username, "1234");
    }
}