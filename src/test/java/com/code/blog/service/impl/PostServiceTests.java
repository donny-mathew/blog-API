package com.code.blog.service.impl;

import com.code.blog.entity.Post;
import com.code.blog.exception.ResourceNotFoundException;
import com.code.blog.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;

    @BeforeEach
    public void setup() {
        post = Post.builder()
                .id(1L)
                .title("Test Title")
                .description("Test description")
                .content("Test content")
                .build();
    }

    @DisplayName("Test case for create post in service layer")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenCreatePost_thenReturnPostObject() {

        given(postRepository.save(post)).willReturn(post);

        Post savedPost = postService.createPost(post);

        Assertions.assertThat(savedPost).isNotNull();
    }

    @DisplayName("Test case for get post by id in service layer")
    @Test
    @SuppressWarnings("null")
    public void givenPostId_whenGetPostById_thenReturnPostObject() {

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        Post savedPost = postService.getPostById(post.getId());

        Assertions.assertThat(savedPost).isNotNull();
    }

    @DisplayName("Test case to updatePost in service layer - success")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenUpdatePost_thenReturnUpdatedPost() {
        Post postToUpdate = Post.builder()
                .title("Test Title Updated")
                .description("Test description Updated")
                .content("Test content Updated")
                .build();
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(postRepository.save(post)).willReturn(post);

        Post updatedPost = postService.updatePost(postToUpdate, post.getId());

        Assertions.assertThat(updatedPost).isNotNull();
        Assertions.assertThat(updatedPost.getContent()).isEqualTo("Test content Updated");
    }

    @DisplayName("Test case to updatePost in service layer - Exception")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenUpdatePost_thenThrowException() {
        Post postToUpdate = Post.builder()
                .title("Test Title Updated")
                .description("Test description Updated")
                .content("Test content Updated")
                .build();
        given(postRepository.findById(post.getId())).willReturn(Optional.empty());
        // given(postRepository.save(post)).willReturn(post);

        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () -> postService.updatePost(postToUpdate, post.getId()));

        verify(postRepository, never()).save(post);
    }

    @DisplayName("Test case to delete post in service layer - success")
    @Test
    @SuppressWarnings("null")
    public void givenPostId_whenDeletePost_thenNothing() {
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        willDoNothing().given(postRepository).delete(post);

        postService.deletePost(1L);

        verify(postRepository, times(1)).delete(post);
    }

}