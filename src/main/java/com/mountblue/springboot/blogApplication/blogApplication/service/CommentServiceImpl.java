package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Comments;
import com.mountblue.springboot.blogApplication.blogApplication.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void createComment(Comments comment, Long postId){
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Override
    public List<Comments> getAllCommentList() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public void getDeleteCommentById(Long id) {
        System.out.println(id);
        commentRepository.delete(id);
    }

    @Override
    @Transactional
    public void updateCommentById(Long id, String name, String email, String comment) {
        Comments existingComment = commentRepository.findById(id).orElseThrow(()->new RuntimeException("Run Time Error"));

        existingComment.setName(name);
        existingComment.setEmail(email);
        existingComment.setComment(comment);
        existingComment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(existingComment);
    }

    @Override
    public Comments getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(()->new RuntimeException("Run Time Error"));
    }

}