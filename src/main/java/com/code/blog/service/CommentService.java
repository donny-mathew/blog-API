package com.code.blog.service;

import com.code.blog.entity.Comment;

import java.util.List;

import org.springframework.lang.NonNull;

public interface CommentService {
    public Comment addComment(@NonNull Long postId, @NonNull Comment comment);

    public List<Comment> getComments(@NonNull Long postId);

    public Comment getCommentById(@NonNull Long postId, @NonNull Long commentId);

    public Comment updateComment(@NonNull Long postId, @NonNull Long commentId, @NonNull Comment comment);

    public void deleteComment(@NonNull Long postId, @NonNull Long commentId);
}
