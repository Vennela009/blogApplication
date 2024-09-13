package com.mountblue.springboot.blogApplication.blogApplication.repository;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Posts,Long> {
    @Query("SELECT DISTINCT p FROM Posts p "+
            "LEFT JOIN p.tags t "+
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search,'%')) OR "+
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :search,'%')) OR "+
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :search,'%')) OR "+
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :search,'%'))"
    )
    Page<Posts> search(@Param("search") String search,Pageable pageable);

    @Query("Select DISTINCT p.author from Posts p ")
    Set<String> getAuthor();

    @Query("Select DISTINCT t.name from Tags t ")
    Set<String> getTagName();


    @Query("SELECT DISTINCT p FROM Posts p " +
            "Left JOIN p.tags t " +
            "WHERE (:authors IS NULL OR p.author IN :authors) AND " +
            "(:tagNames IS NULL OR t.name IN :tagNames) AND " +
            "(:publishedDate IS NULL OR DATE(p.publishedAt) = :publishedDate)")
    Page<Posts> getPostsByRequirement(@Param("authors") Set<String> authors, @Param("tagNames") Set<String> tagNames, @Param("publishedDate") Date publishedDate, Pageable pageable);
}
