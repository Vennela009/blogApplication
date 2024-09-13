package com.mountblue.springboot.blogApplication.blogApplication.controller;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Comments;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Posts;
import com.mountblue.springboot.blogApplication.blogApplication.service.CommentService;
import com.mountblue.springboot.blogApplication.blogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService , PostService postService){
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/comment/{postId}")
    public String createComment(@PathVariable Long postId, Comments comment,Model model){
        comment.setCreatedAt(LocalDateTime.now());

        commentService.createComment(comment,postId);

        Posts post = postService.getPostById(postId);

        model.addAttribute("post",post);

        return "posts/view-post";
    }

    @GetMapping("/comments")
    public String displayComments(Model model){
        List<Comments> comments = commentService.getAllCommentList();

        model.addAttribute("comments", comments);

        return "posts/view-post";
    }

    @GetMapping("/posts/{postId}/comment/edit/{id}")
    public  String displayUpdateHtml(@PathVariable Long postId,@PathVariable Long id, Model model){
        Comments comment = commentService.getCommentById(id);

        model.addAttribute("comment",comment);
        model.addAttribute("postId",postId);
        return "comments/update-comment";
    }

    @GetMapping("/posts/{postId}/comment/delete/{id}")
    public String deleteComment(@PathVariable Long postId ,@PathVariable Long id){
        commentService.getDeleteCommentById(id);

        return "redirect:/posts/" + postId;
    }


    @PostMapping("/posts/{postId}/comment/update/{id}")
    public  String updateComment(@PathVariable Long postId,@PathVariable Long id, @RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("comment") String comment){
        commentService.updateCommentById(id,name,email,comment);

        return "redirect:/posts/" + postId;
    }
}

