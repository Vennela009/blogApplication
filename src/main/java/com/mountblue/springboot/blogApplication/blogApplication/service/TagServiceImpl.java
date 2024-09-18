package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Post;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Tag;
import com.mountblue.springboot.blogApplication.blogApplication.repository.PostRepository;
import com.mountblue.springboot.blogApplication.blogApplication.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{
    private  final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> createTags(String tags) {
        Set<Tag> tagSet = new HashSet<>();

        String[] tagNames = tags.split(",");

        for(String tagName : tagNames){
            Tag newTag = new Tag();
            newTag.setName(tagName);
            newTag.setCreatedAt(LocalDateTime.now());
            tagRepository.save(newTag);
            tagSet.add(newTag);
        }
        return tagSet;
    }


}
