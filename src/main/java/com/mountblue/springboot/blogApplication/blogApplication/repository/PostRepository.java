package com.mountblue.springboot.blogApplication.blogApplication.repository;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT DISTINCT p FROM Post p " +
            "LEFT JOIN p.tags t " +
            "LEFT JOIN p.author a " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Post> search(@Param("search") String search, Pageable pageable);


    @Query("Select DISTINCT a.name from Post p Left Join p.author a")
    Set<String> getAuthor();

    @Query("Select DISTINCT t.name from Post p "+
            "Left Join p.tags  t ")
    Set<String> getTagName();


    @Query("SELECT DISTINCT p FROM Post p " +
            "LEFT JOIN p.tags t " +
            "LEFT JOIN p.author a " +
            "WHERE (:authors IS NULL OR a.name IN :authors) AND " +
            "(:tagNames IS NULL OR t.name IN :tagNames) AND " +
            "(:publishedDate IS NULL OR FUNCTION('DATE', p.publishedAt) = :publishedDate)")
    Page<Post> getPostsByRequirement(@Param("authors") Set<String> authors,
                                      @Param("tagNames") Set<String> tagNames,
                                      @Param("publishedDate") Date publishedDate,
                                      Pageable pageable);

}
