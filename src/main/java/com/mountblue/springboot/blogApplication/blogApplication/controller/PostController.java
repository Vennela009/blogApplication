package com.mountblue.springboot.blogApplication.blogApplication.controller;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Posts;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Tags;
import com.mountblue.springboot.blogApplication.blogApplication.entity.User;
import com.mountblue.springboot.blogApplication.blogApplication.service.PostService;
import com.mountblue.springboot.blogApplication.blogApplication.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")

public class PostController {
    private static final int SIZE =10;
    private final  PostService postService;
    private  final SecurityService securityService;

    @Autowired
    public PostController(PostService postService,SecurityService securityService){
        this.postService = postService;
        this.securityService = securityService;
    }

    @GetMapping("/")
    public  String showCreatePostForm(Model model){
        model.addAttribute("post",new Posts());

        return "posts/create-post";
    }

    @PostMapping("/new")
    public  String createNewPost(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt , @RequestParam("content") String content, @RequestParam(value = "author",required = false) String author, @RequestParam("tags") String tags,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(author==null){
            String existingUserName  = authentication.getName();
            author = existingUserName;
        }
        Set<String> authorsSet = securityService.getUserName();
        if(authorsSet.contains(author)){
            postService.createPost(title,excerpt,content,author,tags);
            return  "redirect:/posts/all";
        }else{
            model.addAttribute("error","Author is not register");
            return "posts/create-post";
        }
    }

    @GetMapping("/all")
    public  String getAllPost(@RequestParam(name="page",defaultValue = "0") int page , Model model){
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Posts> pagePosts = postService.getAllPostByPagination(pageable);

        Set<String> authorSet = postService.findAllAuthor();
        Set<String> tagNameSet = postService.findAllTagName();

        model.addAttribute("authors",authorSet);
        model.addAttribute("tagNames",tagNameSet);

        String previousLink = page>0 ? "/posts/all?page=" + (page-1) +"&limit=10": null;
        String nextLink = page<(pagePosts.getTotalPages()-1)? "/posts/all?page=" + (page+1)+"&limit=10":null;

        model.addAttribute("previousLink",previousLink);
        model.addAttribute("nextLink",nextLink);

        model.addAttribute("posts",pagePosts.getContent());
        model.addAttribute("page",page);
        model.addAttribute("allList",true);
        model.addAttribute("filterPagination",false);

        return "posts/allList-post";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model){
        Posts post = postService.getPostById(id);
        model.addAttribute("post",post);

        return "posts/view-post";
    }

    @GetMapping("/edit/{id}")
    public String getPostDetails(@PathVariable Long id,Model model){
        Posts post = postService.getPostById(id);

        String tags = "";
        for(Tags each: post.getTags()){
            tags += each.getName() + ",";
        };

        tags = tags.replaceAll(",$","");

        model.addAttribute("post",post);

        model.addAttribute("presentTag",tags);

        return "posts/edit-post";
    }

    @PostMapping("/update/{id}")
    public String updatePostDetails(@PathVariable Long id , @RequestParam("title") String title, @RequestParam("content") String content,@RequestParam("tags") String tags,Model model){
        postService.updatePostById(id,title,content,tags);

        System.out.println("hi");
        Posts updatePost = postService.getPostById(id);

        model.addAttribute("post",updatePost);

        return "posts/view-post";

    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id,Model model){
        postService.deletePostById(id);
        
        List<Posts> posts = postService.getAllPost();

        model.addAttribute("posts",posts);

        return "redirect:/posts/all";
    }

    @GetMapping("/sort/{order}")
    public String sortOrderOfPosts(@PathVariable String order,@RequestParam(value="page" , defaultValue = "0") int page,Model model){
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Posts> orderPagePost = postService.getAllPostBySort(order,pageable);

        Set<String> authorSet = postService.findAllAuthor();
        Set<String> tagNameSet = postService.findAllTagName();

        String previousLink = page>0? "/posts/sort/"+order + "?page=" + (page-1)+"&limit=10" : null;
        String nextLink = page< (orderPagePost.getTotalPages()-1)?"/posts/sort/"+order + "?page="+(page+1)+"&limit=10":null;

        model.addAttribute("authors",authorSet);
        model.addAttribute("tagNames",tagNameSet);

        model.addAttribute("previousLink",previousLink);
        model.addAttribute("nextLink",nextLink);

        model.addAttribute("posts",orderPagePost.getContent());
        model.addAttribute("page",page);
        model.addAttribute("allList",false);
        model.addAttribute("filterPagination",false);
        model.addAttribute("order",order);

        return "posts/allList-post";
    }

    @GetMapping("/search")
    public String searchByRequirement(@RequestParam("search") String search, @RequestParam(value="page",defaultValue = "0") int page,Model model){
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Posts> allPostsFromSearch = postService.getSearchContent(search,pageable);

        String previousLink =  page>0? "/posts/search?search="+search+ "&page=" + (page-1)+"&limit=10" : null;
        String nextLink = page<(allPostsFromSearch.getTotalPages()-1)?"/posts/search?search="+search+"&page="+(page+1)+"&limit=10":null;


        Set<String> authorSet = postService.findAllAuthor();
        Set<String> tagNameSet = postService.findAllTagName();

        model.addAttribute("authors",authorSet);
        model.addAttribute("tagNames",tagNameSet);

        model.addAttribute("previousLink",previousLink);
        model.addAttribute("nextLink",nextLink);

        model.addAttribute("posts", allPostsFromSearch.getContent());
        model.addAttribute("page",page);
        model.addAttribute("searchQuery",search);
        model.addAttribute("allList",false);
        model.addAttribute("filterPagination",false);

        return "posts/allList-post";
    }

    @GetMapping("/filter")
    public String filterByAuthorAndTagName(@RequestParam(value="page",defaultValue = "0") int page, @RequestParam(value="author", required = false) Set<String> authors, @RequestParam(value="tagName",required = false) Set<String> tagNames, @RequestParam(value = "publishedAt",required = false) LocalDate publishedDate, Model model ){
        Pageable pageable = PageRequest.of(page,SIZE);
        Page<Posts> filterPosts;

        Set<String> decodedAuthors = decodeSet(authors);
        Set<String> decodedTagName = decodeSet(tagNames);

        if ((authors==null || authors.isEmpty()) && ( tagNames == null || tagNames.isEmpty()) && (publishedDate==null)) {
            filterPosts = postService.getAllPostByPagination(pageable);

        } else {
            filterPosts = postService.getPostsByFilter(decodedAuthors, decodedTagName, publishedDate, pageable);

        }
        Set<String> authorSet = postService.findAllAuthor();
        Set<String> tagNameSet = postService.findAllTagName();

        model.addAttribute("authors",authorSet);
        model.addAttribute("tagNames",tagNameSet);

        model.addAttribute("authorLink",decodedAuthors);
        model.addAttribute("tagNameLink",decodedTagName);
        model.addAttribute("publishedDateLink",publishedDate);

        model.addAttribute("posts",filterPosts.getContent());
        model.addAttribute("page",page);
        model.addAttribute("allList",false);
        model.addAttribute("totalPages",filterPosts.getTotalPages());
        model.addAttribute("filterPagination",true);

        return "posts/allList-post";
    }

    private Set<String> decodeSet(Set<String> encoded) {
        if (encoded==null){
            return null;
        }
        return encoded.stream()
                .map(s-> URLDecoder.decode(s, StandardCharsets.UTF_8))
                .collect(Collectors.toSet());
    }

}
