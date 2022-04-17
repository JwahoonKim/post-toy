package com.flab.posttoy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.postDto.PostRequestDto;
import com.flab.posttoy.repository.postRepository.MemoryPostRepository;
import com.flab.posttoy.repository.postRepository.PostRepository;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    // 테스트를 작성하다보면 공통적으로 given 해야하는 것들이 있는데..
    // ex.) 유저 저장하고 글 최소 하나는 만들어야 삭제, 수정, 조회 등을 할 수 있음
    // 여기서 유저 저장 + 글 생성은 공통적으로 생김

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserService userService;
    @Autowired PostService postService;
    @Autowired UserRepository userRepository;
    @Autowired PostRepository postRepository;


    // 전체 테스트하면 실패하는 이유..?
    // beforeEach, afterEach 어떻게 써줘야하나
    @BeforeEach
    void beforeEach() {
        userRepository = new MemoryUserRepository();
        postRepository = new MemoryPostRepository();
    }

    @AfterEach
    void afterEach() {
//        userRepository.clearStore();
//        postRepository.clearStore();
//        userRepository = new MemoryUserRepository();
//        postRepository = new MemoryPostRepository();
    }

    @Test
    public void 글_등록_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        // controller를 테스트하는데 service계층을 가져와야해서 찝찝..
        // user가 없으면 post를 작성할 수가 없어서 이 부분 해결해야할듯
        userService.addUser(user);
        PostRequestDto postRequestDto = new PostRequestDto(user.getId(), "title1", "content1");
        String content = objectMapper.writeValueAsString(postRequestDto);

        //when
        ResultActions response = mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("title1"))
                .andExpect(jsonPath("content").value("content1"))
                .andExpect(jsonPath("username").value("user1"));
    }

    @Test
    public void 글_상세_조회_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        PostRequestDto postRequestDto = new PostRequestDto(user.getId(), "title1", "content1");
        postService.addPost(postRequestDto.toEntity(user));

        //when
        ResultActions response = mvc.perform(get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("title1"))
                .andExpect(jsonPath("content").value("content1"))
                .andExpect(jsonPath("username").value("user1"));
    }

    @Test
    public void 글_전부_조회_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        PostRequestDto postRequestDto1 = new PostRequestDto(user.getId(), "title1", "content1");
        PostRequestDto postRequestDto2 = new PostRequestDto(user.getId(), "title2", "content2");
        postService.addPost(postRequestDto1.toEntity(user));
        postService.addPost(postRequestDto2.toEntity(user));

        //when
        ResultActions response = mvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("title1"))
                .andExpect(jsonPath("$.data[0].content").value("content1"));
    }

    @Test
    public void 글_삭제_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        PostRequestDto postRequestDto1 = new PostRequestDto(user.getId(), "title1", "content1");
        PostRequestDto postRequestDto2 = new PostRequestDto(user.getId(), "title2", "content2");
        postService.addPost(postRequestDto1.toEntity(user));
        postService.addPost(postRequestDto2.toEntity(user));

        //when
        ResultActions response = mvc.perform(delete("/posts/2")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("title").value("title2"))
                .andExpect(jsonPath("content").value("content2"))
                .andExpect(jsonPath("username").value("user1"));
    }

    @Test
    public void 글_수정_테스트() throws Exception {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        PostRequestDto postRequestDto1 = new PostRequestDto(user.getId(), "title1", "content1");
        postService.addPost(postRequestDto1.toEntity(user));

        PostRequestDto postRequestDto = new PostRequestDto(user.getId(), "new title", "new content");
        String content = objectMapper.writeValueAsString(postRequestDto);

        //when
        ResultActions response = mvc.perform(patch("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("new title"))
                .andExpect(jsonPath("content").value("new content"))
                .andExpect(jsonPath("username").value("user1"));
    }

    @Test
    public void 특정_유저가_쓴_글_전부_조회_테스트() throws Exception {
        //given
        User user1 = makeUser("user1");
        User user2 = makeUser("user2");
        userService.addUser(user1);
        PostRequestDto postRequestDto1 = new PostRequestDto(user1.getId(), "title1", "content1");
        PostRequestDto postRequestDto2 = new PostRequestDto(user1.getId(), "title2", "content2");
        PostRequestDto postRequestDto3 = new PostRequestDto(user2.getId(), "title3", "content3");
        postService.addPost(postRequestDto1.toEntity(user1));
        postService.addPost(postRequestDto2.toEntity(user1));
        postService.addPost(postRequestDto2.toEntity(user2));

        //when
        ResultActions response = mvc.perform(get("/users/1/posts")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("title1"))
                .andExpect(jsonPath("$.data[0].content").value("content1"));
    }

    private User makeUser(String username) {
        return new User("user1", "1234");
    }

}