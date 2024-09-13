package com.mountblue.springboot.blogApplication.blogApplication.repository;

import com.mountblue.springboot.blogApplication.blogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SecurityRepository extends JpaRepository<User,Long> {
    @Query("SELECT DISTINCT u.name from User u")
    Set<String> findAllUserName();

    @Query("SELECT DISTINCT u.email from User u")
    Set<String> finalAllEmail();
}
