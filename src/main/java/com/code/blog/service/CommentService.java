package com.code.blog.service;

import com.code.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    public Comment addComment(Long postId, Comment comment);
    public List<Comment> getComments(Long postId);
    public Comment getCommentById(Long postId, Long commentId);
    public Comment updateComment(Long postId, Long commentId, Comment comment);
    public void deleteComment(Long postId, Long commentId);
}
