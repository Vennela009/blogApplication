package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void createComment(Comments comment, Long postId);

    List<Comments> getAllCommentList();

    void getDeleteCommentById(Long id);

    Comments getCommentById(Long id);

    void updateCommentById(Long id, String name, String email, String comment);
}
