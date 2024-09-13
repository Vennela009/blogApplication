package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface PostService {
    void createPost(String title, String excerpt, String content, String author, String tags);

    Posts getPostById(Long id);

    void updatePostById(Long id, String title, String content, String tags);

    void deletePostById(Long id);

    Page<Posts> getAllPostBySort(String order, Pageable pageable);

    Page<Posts> getSearchContent(String search,Pageable pageable);

    Page<Posts> getAllPostByPagination(Pageable pageable);

    Set<String> findAllAuthor();

    Set<String> findAllTagName();

    Page<Posts> getPostsByFilter(Set<String> authors, Set<String> tagNames, LocalDate publishedDates ,Pageable pageable);

    List<Posts> getAllPost();

}
