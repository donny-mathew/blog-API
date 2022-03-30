package com.code.blog.service.impl;

import com.code.blog.entity.Comment;
import com.code.blog.entity.Post;
import com.code.blog.exception.BlogAPIException;
import com.code.blog.exception.ResourceNotFoundException;
import com.code.blog.repository.CommentRepository;
import com.code.blog.repository.PostRepository;
import com.code.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public Comment addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+postId));

        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: "+commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException("Comment does not belong to post");
        return comment;
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, Comment comment) {
        Comment commentToUpdate = this.getCommentById(postId, commentId);

        commentToUpdate.setName(comment.getName());
        commentToUpdate.setEmail(comment.getEmail());
        commentToUpdate.setBody(comment.getBody());

        return commentRepository.save(commentToUpdate);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment commentToDelete = this.getCommentById(postId, commentId);
        commentRepository.delete(commentToDelete);
    }
}
