package com.code.blog.controller;

import com.code.blog.entity.Comment;
import com.code.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    @SuppressWarnings("null")
    public ResponseEntity<Comment> addComment(@PathVariable(value = "postId") Long postId,
            @Valid @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.addComment(postId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    @SuppressWarnings("null")
    public ResponseEntity<List<Comment>> getComments(@PathVariable(value = "postId") Long postId) {
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    @SuppressWarnings("null")
    public ResponseEntity<Comment> getCommentByPostIdCommentId(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SuppressWarnings("null")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, comment), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SuppressWarnings("null")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }

}
