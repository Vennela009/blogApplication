package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface SecurityService {
    void newUserRegister(User user);

    Set<String> getUserName();

    Set<String> getEmail();

    User getUserDetails(String name);
}
