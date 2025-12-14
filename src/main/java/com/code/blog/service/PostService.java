package com.code.blog.service;

import com.code.blog.entity.Post;
import com.code.blog.payload.PostResponse;

import org.springframework.lang.NonNull;

public interface PostService {
    public Post createPost(@NonNull Post post);

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public Post getPostById(@NonNull Long id);

    public Post updatePost(@NonNull Post post, @NonNull Long id);

    public void deletePost(@NonNull Long id);
}
