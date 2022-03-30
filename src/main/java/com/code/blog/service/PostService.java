package com.code.blog.service;

import com.code.blog.entity.Post;
import com.code.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    public Post createPost(Post post);

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public Post getPostById(Long id);

    public Post updatePost(Post post, Long id);

    public void deletePost(Long id);
}
