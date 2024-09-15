package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Posts;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Tags;
import com.mountblue.springboot.blogApplication.blogApplication.entity.User;
import com.mountblue.springboot.blogApplication.blogApplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements  PostService{
    private  final PostRepository postRepository;
    private  final TagService tagService;
    private  final  SecurityService securityService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,TagService tagService,SecurityService securityService){
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.securityService = securityService;
    }

    @Override
    @Transactional
    public void createPost(String title, String excerpt, String content, String author, String tags) {
        Posts post = new Posts();
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        User existingAuthor = securityService.getUserDetails(author);
        post.setAuthor(existingAuthor);
        post.setPublishedAt(LocalDateTime.now());
        post.setIsPublished(true);
        post.setCreatedAt(LocalDateTime.now());
        Set<Tags> tagsSet = tagService.createTags(tags);
        post.setTags(tagsSet);

        postRepository.save(post);
    }

    @Override
    public Posts getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()-> new RuntimeException("Error is at run time"));
    }

    @Override
    @Transactional
    public void updatePostById(Long id, String title, String content ,String tags) {
        Posts existingPost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Error is occurred at updating the details"));

        existingPost.setTitle(title);
        existingPost.setContent(content);
        existingPost.setUpdatedAt(LocalDateTime.now());
        Set<Tags> tagsSet = tagService.createTags(tags);
        existingPost.setTags(tagsSet);

        postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Posts> getAllPostBySort(String order,Pageable pageable) {
        Sort.Direction direction = order.equals("ASC")?Sort.Direction.ASC: Sort.Direction.DESC;
        Sort sort = Sort.by(direction,"publishedAt");

        Pageable sortedAndPaged = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return postRepository.findAll(sortedAndPaged);
    }

    @Override
    public Page<Posts> getSearchContent(String search,Pageable pageable) {
        return  postRepository.search(search,pageable);
    }

    @Override
    public Page<Posts> getAllPostByPagination(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC,"publishedAt");

        Pageable sortedAndPaged = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sort);

        return postRepository.findAll(sortedAndPaged);
    }

    @Override
    public Set<String> findAllAuthor() {
        return postRepository.getAuthor();
    }

    @Override
    public Set<String> findAllTagName() {
        return postRepository.getTagName();
    }

    @Override
    public Page<Posts> getPostsByFilter(Set<String> authors, Set<String> tagNames, LocalDate publishedDate, Pageable pageable) {
        Date date = null;

        if (publishedDate != null) {
            date = Date.from(publishedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        Set<String> safeAuthors = (authors != null && !authors.isEmpty()) ? authors : null;
        Set<String> safeTagNames = (tagNames != null && !tagNames.isEmpty()) ? tagNames : null;

        return postRepository.getPostsByRequirement(safeAuthors, safeTagNames, date, pageable);
    }

    @Override
    public List<Posts> getAllPost() {
        return postRepository.findAll();
    }

}
