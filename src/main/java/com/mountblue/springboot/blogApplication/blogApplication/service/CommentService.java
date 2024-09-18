package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void createComment(Comment comment, Long postId);

    List<Comment> getAllCommentList();

    void getDeleteCommentById(Long id,Long postId);

    Comment getCommentById(Long id);

    void updateCommentById(Long id, String comment);
}
