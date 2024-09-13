package com.mountblue.springboot.blogApplication.blogApplication.repository;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tags,Long> {
    Optional<Tags> findByName(String tagName);
}
