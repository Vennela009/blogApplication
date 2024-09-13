package com.mountblue.springboot.blogApplication.blogApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Post")
@Getter
@Setter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    private String excerpt;

    @Column(nullable = false,length = 10000)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(name="published_at")
    private LocalDateTime publishedAt;

    @Column(name="is_published")
    private Boolean isPublished=false;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private  LocalDateTime updatedAt;

    @OneToMany(mappedBy="post", cascade ={CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<Comments> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="post_tags",
    joinColumns = @JoinColumn(name="post_id"),
    inverseJoinColumns = @JoinColumn(name="tag_id"))
    private Set<Tags> tags = new HashSet<>();

    public Posts(){
    }

    public Posts(Long id, String title, String excerpt, String content, String author, LocalDateTime publishedAt, Boolean isPublished, LocalDateTime createdAt, LocalDateTime updatedAt, List<Comments> comments, Set<Tags> tags) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.publishedAt = publishedAt;
        this.isPublished = isPublished;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.comments = comments;
        this.tags = tags;
    }

}
