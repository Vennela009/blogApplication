package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.Posts;
import com.mountblue.springboot.blogApplication.blogApplication.entity.Tags;
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
    private  final PostRepository postRepository;

    public TagServiceImpl(TagRepository tagRepository,PostRepository postRepository){
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Set<Tags> createTags(String tags) {
        Set<Tags> tagSet = new HashSet<>();

        String[] tagNames = tags.split(",");

        for(String tagName : tagNames){
            Tags newTag = new Tags();
            newTag.setName(tagName);
            newTag.setCreatedAt(LocalDateTime.now());
            tagRepository.save(newTag);
            tagSet.add(newTag);
        }
        return tagSet;
    }

    @Override
    public Set<Tags> updateTags(String tags, Long id) {
        Set<String> newTagNames = Arrays.stream(tags.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        Posts post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        Set<Tags> existingTags = new HashSet<>(post.getTags());
        Set<Tags> updatedTags = new HashSet<>();

        for (String tagName : newTagNames) {
            Tags tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tags newTag = new Tags();
                        newTag.setName(tagName);
                        newTag.setCreatedAt(LocalDateTime.now());
                        return tagRepository.save(newTag);
                    });

            tag.setUpdatedAt(LocalDateTime.now());
            updatedTags.add(tag);

        }

        existingTags.removeIf(tag -> !newTagNames.contains(tag.getName()));

        post.setTags(existingTags);
        postRepository.save(post);

        return updatedTags;
    }

}
