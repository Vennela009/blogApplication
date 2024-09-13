package com.mountblue.springboot.blogApplication.blogApplication.repository;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
}
