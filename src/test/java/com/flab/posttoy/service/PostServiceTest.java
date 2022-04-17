package com.flab.posttoy.service;

import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.dto.postDto.PostDetatilResponseDto;
import com.flab.posttoy.dto.postDto.PostRequestDto;
import com.flab.posttoy.dto.postDto.PostResponseDto;
import com.flab.posttoy.repository.postRepository.MemoryPostRepository;
import com.flab.posttoy.repository.postRepository.PostRepository;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//@SpringBootTest
class PostServiceTest {

    UserRepository userRepository = new MemoryUserRepository();
    PostRepository postRepository = new MemoryPostRepository();

    PostService postService = new PostService(userRepository, postRepository);
    UserService userService = new UserService(userRepository);

    @AfterEach
    // clearStore 하기 위해서는 userRepository가 있어야하는데 그러면 위에서 Autowired하면 안되나..?
    // Autowired 하고 아래처럼 메모리 비울수있는 방법 질문.
    void afterEach() {
        userRepository.clearStore();
        postRepository.clearStore();
    }

    @Test
    public void 글_등록_테스트() {
        //given
        User user = makeUser("user1");
        userService.addUser(user);
        Post post = new Post("title1", "content1", user);

        //when
        postService.addPost(post);

        //then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("title1");
        assertThat(post.getContent()).isEqualTo("content1");
        assertThat(post.getUser().getUsername()).isEqualTo("user1");
    }

    @Test
    public void 글_조회_테스트() {
        //given
        User user = makeUser("user1");
        userRepository.insertUser(user);
        Post post = new Post("title1", "content1", user);
        Long postId = postService.addPost(post).getId();

        //when
        Post findPost = postService.findPost(postId);

        //then
        assertThat(findPost.getId()).isEqualTo(1L);
        assertThat(findPost.getTitle()).isEqualTo("title1");
        assertThat(findPost.getContent()).isEqualTo("content1");
        assertThat(findPost.getUser().getUsername()).isEqualTo("user1");
        assertThat(findPost.getComments().size()).isEqualTo(0);
    }

    @Test
    public void 글_전부_조회_테스트() {
        //given
        User user = makeUser("user1");
        userRepository.insertUser(user);
        Post post1 = new Post("title1", "content1", user);
        Post post2 = new Post("title2", "content2", user);
        Post res1 = postService.addPost(post1);
        Post res2 = postService.addPost(post2);

        //when
        List<Post> posts = postService.findAllPosts();

        //then
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts.get(0).getTitle()).isEqualTo("title1");
        assertThat(posts.get(0).getContent()).isEqualTo("content1");
        assertThat(posts.get(1).getTitle()).isEqualTo("title2");
        assertThat(posts.get(1).getContent()).isEqualTo("content2");
    }

    @Test
    public void 글_삭제_테스트() {
        //given
        User user = makeUser("user1");
        userRepository.insertUser(user);
        Post req1 = new Post("title1", "content1", user);
        Post req2 = new Post("title2", "content2", user);
        Post res1 = postService.addPost(req1);
        Post res2 = postService.addPost(req2);

        //when
        Post deletedPost = postService.removePost(res1.getId());

        //then
        assertThat(deletedPost.getTitle()).isEqualTo("title1");
        assertThat(deletedPost.getContent()).isEqualTo("content1");
        assertThat(postService.findAllPosts().size()).isEqualTo(1);
        assertThat(postService.findAllPosts().get(0).getContent()).isEqualTo("content2");
    }

    @Test
    public void 글_수정_테스트() {
        //given
        User user = makeUser("user1");
        userRepository.insertUser(user);
        Post post = new Post("title1", "content1", user);
        postService.addPost(post);

        //when
        Post updatedPost = new Post("new title", "new content", user);
        postService.modifyPost(post.getId(), updatedPost);

        //then
        assertThat(postService.findAllPosts().get(0).getTitle()).isEqualTo("new title");
        assertThat(postService.findAllPosts().get(0).getContent()).isEqualTo("new content");
    }

    @Test
    public void 특정_유저가_쓴_글_조회_테스트() {
        //given
        User user1 = makeUser("user1");
        User user2 = makeUser("user2");
        userService.addUser(user1);
        userService.addUser(user2);

        Post post1 = new Post("title1", "content1", user1);
        Post post2 = new Post("title1", "content1", user1);
        Post post3 = new Post("title3", "content3", user2);
        postService.addPost(post1);
        postService.addPost(post2);
        postService.addPost(post3);

        //when
        List<Post> postsByUser1 = postService.findPostsByUser(user1.getId());
        List<Post> postsByUser2 = postService.findPostsByUser(user2.getId());

        //then
        assertThat(postsByUser1.size()).isEqualTo(2);
        assertThat(postsByUser2.size()).isEqualTo(1);
    }

    private User makeUser(String username) {
        return new User(username, "1234");
    }

}