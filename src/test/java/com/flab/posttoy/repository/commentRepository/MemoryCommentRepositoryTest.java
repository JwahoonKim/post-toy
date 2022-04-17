package com.flab.posttoy.repository.commentRepository;

import com.flab.posttoy.domain.Comment;
import com.flab.posttoy.domain.Post;
import com.flab.posttoy.domain.User;
import com.flab.posttoy.repository.postRepository.MemoryPostRepository;
import com.flab.posttoy.repository.userRepository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemoryCommentRepositoryTest {

    MemoryCommentRepository memoryCommentRepository = new MemoryCommentRepository();
    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
    MemoryPostRepository memoryPostRepository = new MemoryPostRepository();

    @AfterEach
    void afterEach() {
        memoryCommentRepository.clearStore();
        memoryUserRepository.clearStore();
        memoryPostRepository.clearStore();
    }

    @Test
    public void 댓글_등록_테스트() {
        //given
        User user = makeUser("user1");
        Post post = makePost("title1", "content1", user);
        Comment comment = new Comment("comment1", user, post);

        //when
        Comment savedComment = memoryCommentRepository.insertComment(comment);
        Long commentId = savedComment.getId();

        //then
        assertThat(comment.getId()).isEqualTo(commentId);
        assertThat(savedComment.getContent()).isEqualTo("comment1");
    }

    @Test
    public void 댓글_삭제_테스트() {
        //given
        User user = makeUser("user1");
        Post post = makePost("title1", "content1", user);
        Comment comment1 = new Comment("comment1", user, post);
        Comment comment2 = new Comment("comment2", user, post);
        Comment savedComment1 = memoryCommentRepository.insertComment(comment1);
        Comment savedComment2 = memoryCommentRepository.insertComment(comment2);
        Long savedId1 = savedComment1.getId();
        Long savedId2 = savedComment2.getId();

        //when
        memoryCommentRepository.deleteComment(savedId1);

        //then
        assertThat(memoryCommentRepository.selectCommentById(savedId1)).isNull();
        assertThat(memoryCommentRepository.selectCommentById(savedId2)).isNotNull();
    }

    @Test
    public void 댓글_수정_테스트() {
        //given
        User user = makeUser("user1");
        Post post = makePost("title1", "content1", user);
        Comment comment = new Comment("comment1", user, post);
        memoryCommentRepository.insertComment(comment);

        //when
        memoryCommentRepository.updateComment(comment.getId(), "new comment");

        //then
        assertThat(comment.getContent()).isEqualTo("new comment");
    }

    private User makeUser(String username) {
        return new User(username, "1234");
    }

    private Post makePost(String title, String content, User user) {
        return new Post(title, content, user);
    }
}