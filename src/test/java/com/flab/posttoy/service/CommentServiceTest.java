package com.flab.posttoy.service;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.repository.commentRepository.CommentRepository;
import com.flab.posttoy.repository.commentRepository.MemoryCommentRepository;
import com.flab.posttoy.repository.postRepository.MemoryPostRepository;
import com.flab.posttoy.repository.postRepository.PostRepository;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import com.flab.posttoy.repository.userRepository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CommentServiceTest {

    UserRepository userRepository = new MemoryUserRepository();
    PostRepository postRepository = new MemoryPostRepository();
    CommentRepository commentRepository = new MemoryCommentRepository();

    PostService postService = new PostService(userRepository, postRepository);
    UserService userService = new UserService(userRepository);
    CommentService commentService = new CommentService(commentRepository, postRepository, userRepository);

    @AfterEach
    // clearStore 하기 위해서는 userRepository가 있어야하는데 그러면 위에서 Autowired하면 안되나..?
    // Autowired 하고 아래처럼 메모리 비울수있는 방법 질문.
    void afterEach() {
        userRepository.clearStore();
        postRepository.clearStore();
    }

    @Test
    public void 댓글_등록() {
        //given
        User user = new User("user1", "1234");
        userService.addUser(user);
        Post post = new Post("title1", "contnet1", user);
        postService.addPost(post);

        //when
        Comment comment1 = new Comment("comment1", user, post);
        Comment comment2 = new Comment("comment2", user, post);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        //then
        assertThat(post.getComments().get(1L)).isEqualTo("comment1");
        assertThat(post.getComments().get(2L)).isEqualTo("comment2");
    }

    @Test
    public void 댓글_삭제() {
        //given
        User user = new User("user1", "1234");
        userService.addUser(user);
        Post post = new Post("title1", "contnet1", user);
        postService.addPost(post);

        Comment comment1 = new Comment("comment1", user, post);
        Comment comment2 = new Comment("comment2", user, post);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        //when
        commentService.removeComment(post.getId(), 1L);

        //then
        assertThat(post.getComments().get(1L)).isNull();
        assertThat(post.getComments().get(2L)).isEqualTo("comment2");
    }

    @Test
    public void 댓글_수정() {
        //given
        //given
        User user = new User("user1", "1234");
        userService.addUser(user);
        Post post = new Post("title1", "contnet1", user);
        postService.addPost(post);

        Comment comment1 = new Comment("comment1", user, post);
        Comment comment2 = new Comment("comment2", user, post);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        Comment newComment = new Comment("new comment", user, post);

        //when
        commentService.modifyComment(post.getId(), comment2.getId(),newComment);

        //then
        assertThat(post.getComments().get(2L)).isEqualTo("new comment");
    }

}
