package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Tags;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface TagService {
    Set<Tags> createTags(String tags);

    Set<Tags> updateTags(String tags,Long id);
}
