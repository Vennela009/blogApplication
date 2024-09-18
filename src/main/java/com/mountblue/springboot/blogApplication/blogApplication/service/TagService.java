package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface TagService {
    Set<Tag> createTags(String tags);

}
