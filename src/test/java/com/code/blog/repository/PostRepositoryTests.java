package com.code.blog.repository;

import com.code.blog.entity.Post;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    // JUnit test for save post
    @DisplayName("Test case for save post")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenSave_thenReturnSavedPost() {

        // given - preconditions or setup
        Post post = Post.builder()
                .title("Test Title")
                .description("Test description")
                .content("Test content")
                .build();

        // when - action or behaviour that we are going to test
        Post savedPost = postRepository.save(post);

        // then - verify the output
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isGreaterThan(0);
    }

    // Junit test for find all posts
    @DisplayName("Test case for get all post")
    @Test
    @SuppressWarnings("null")
    public void givenPostList_whenFindAll_thenReturnPostObject() {

        // given - preconditions or setup
        Post post1 = Post.builder()
                .title("Test Title 1")
                .description("Test description 1")
                .content("Test content 1")
                .build();
        Post post2 = Post.builder()
                .title("Test Title 2")
                .description("Test description 2")
                .content("Test content 2")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        // when - action or behaviour that we are going to test
        List<Post> postList = postRepository.findAll();

        // then - verify the output
        assertThat(postList).isNotNull();
        assertThat(postList).hasSize(2);
    }

    @DisplayName("Test case for find post by Id")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenFindById_thenReturnPostObject() {

        Post post = Post.builder()
                .title("Test Title")
                .description("Test description")
                .content("Test content")
                .build();
        postRepository.save(post);

        Post postDb = postRepository.findById(post.getId()).get();

        assertThat(postDb).isNotNull();
    }

    @DisplayName("Test case for update post with content")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenUpdatePost_thenReturnUpdatedPostObject() {
        Post post = Post.builder()
                .title("Test Title")
                .description("Test description")
                .content("Test content")
                .build();
        postRepository.save(post);

        Post savedPost = postRepository.findById(post.getId()).get();
        savedPost.setContent("Updated Test content");
        Post updatedPost = postRepository.save(savedPost);

        assertThat(updatedPost.getContent()).isEqualTo("Updated Test content");
    }

    @DisplayName("Test case for delete post by Id")
    @Test
    @SuppressWarnings("null")
    public void givenPostObject_whenDelete_thenRemovePost() {
        Post post = Post.builder()
                .title("Test Title")
                .description("Test description")
                .content("Test content")
                .build();
        postRepository.save(post);

        postRepository.deleteById(post.getId());
        Optional<Post> postOptional = postRepository.findById(post.getId());

        assertThat(postOptional).isEmpty();
    }

}