package com.mountblue.springboot.blogApplication.blogApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String email;

    @Column(nullable = false,length=1000)
    private  String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private  Posts post;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private  LocalDateTime updatedAt;



    public Comments(Long id, String name, String email, String comment, Posts post, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name =  name;
        this.email = email;
        this.comment = comment;
        this.post = post;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


}
