package com.flab.posttoy.controller;

import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.userDto.UserSaveRequestDto;
import com.flab.posttoy.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc를 쓰기위해서
class UserControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    public void 유저_등록_테스트() throws Exception {
        //given
        User user = new User("user1", "1234");

        String content = objectMapper.writeValueAsString(new UserSaveRequestDto("user1", "1234"));

        //when
        ResultActions response = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("username").value("user1"));
    }
}