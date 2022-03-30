package com.code.blog.service.impl;

import com.code.blog.entity.Post;
import com.code.blog.exception.ResourceNotFoundException;
import com.code.blog.payload.PostResponse;
import com.code.blog.repository.PostRepository;
import com.code.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.getContent());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+id));
    }

    @Override
    public Post updatePost(Post post, Long id) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+id));

        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setDescription(post.getDescription());
        postToUpdate.setContent(post.getContent());

        return postRepository.save(postToUpdate);
    }

    @Override
    public void deletePost(Long id) {
        Post postToDelete = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+id));

        postRepository.delete(postToDelete);
    }
}
