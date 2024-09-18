package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Comment;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Post;
import com.mountblue.springboot.blogApplication.blogApplication.repository.CommentRepository;
import com.mountblue.springboot.blogApplication.blogApplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void createComment(Comment comment, Long postId){
        comment.setCreatedAt(LocalDateTime.now());
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Run Time Error"));
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentList() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public void getDeleteCommentById(Long id,Long postId) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(()-> new RuntimeException("Run Time Error"));
    }


    @Override
    @Transactional
    public void updateCommentById(Long id, String comment) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(()->new RuntimeException("Run Time Error"));

        existingComment.setComment(comment);
        existingComment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(existingComment);
    }


}