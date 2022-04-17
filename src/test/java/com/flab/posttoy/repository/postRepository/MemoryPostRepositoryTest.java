package com.flab.posttoy.repository.postRepository;

import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryPostRepositoryTest {

    MemoryPostRepository memoryPostRepository = new MemoryPostRepository();
    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @AfterEach
    void afterEach() {
        memoryPostRepository.clearStore();
        memoryUserRepository.clearStore();
    }

    @Test
    void 글_등록_테스트(){
        //given
        User user = makeUser("user1");
        Post post = new Post("title1", "content", user);

        //when
        Post savedPost = memoryPostRepository.insertPost(post);

        //then
        Post findPost = memoryPostRepository.selectPostById(savedPost.getId());
        assertThat(savedPost).isEqualTo(findPost);
        assertThat(findPost.getId()).isEqualTo(1);
    }

    @Test
    public void 글_삭제_테스트() {
        //given
        User user = makeUser("user1");
        Post post = new Post("title1", "content", user);
        memoryPostRepository.insertPost(post);

        //when
        memoryPostRepository.deletePost(1L);

        //then
        assertThat(memoryPostRepository.selectPostById(1L)).isNull();
    }

    @Test
    public void 글_수정_테스트() {
        //given
        User user = makeUser("user1");
        Post post = new Post("title1", "content", user);
        memoryPostRepository.insertPost(post);

        //when
        Post updatedPost = memoryPostRepository.updatePost(1L, "new title", "new content");
        Post findPost = memoryPostRepository.selectPostById(1L);

        //then
        assertThat(findPost.getTitle()).isEqualTo(updatedPost.getTitle());
        assertThat(findPost.getContent()).isEqualTo(updatedPost.getContent());
    }

    @Test
    public void 글_전부_조회_테스트() {
        //given
        User user = makeUser("user1");
        Post post1 = new Post("title1", "content1", user);
        Post post2 = new Post("title2", "content2", user);
        memoryPostRepository.insertPost(post1);
        memoryPostRepository.insertPost(post2);

        //when
        List<Post> posts = memoryPostRepository.selectAllPosts();

        //then
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts).contains(post1, post2);
    }

    @Test
    public void 특정_유저가_쓴_글_조회_테스트() {
        //given
        User user1 = makeUser("user1");
        User user2 = makeUser("user2");
        memoryUserRepository.insertUser(user1);
        memoryUserRepository.insertUser(user2);

        Post post1 = new Post("title1", "content1", user1);
        Post post2 = new Post("title2", "content2", user2);
        memoryPostRepository.insertPost(post1);
        memoryPostRepository.insertPost(post2);

        //when
        Long userId = user1.getId();
        List<Post> postsByUser = memoryPostRepository.selectAllPostsByUser(userId);

        //then
        assertThat(postsByUser.size()).isEqualTo(1);
        assertThat(postsByUser).contains(post1);
    }


    private User makeUser(String username) {
        return new User(username, "1234");
    }

}