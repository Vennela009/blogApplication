package com.mountblue.springboot.blogApplication.blogApplication.service;

import com.mountblue.springboot.blogApplication.blogApplication.entity.User;
import com.mountblue.springboot.blogApplication.blogApplication.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final SecurityRepository securityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityServiceImpl(SecurityRepository userRepository,PasswordEncoder passwordEncoder){
        this.securityRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void newUserRegister(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        securityRepository.save(user);
    }

    @Override
    public Set<String> getUserName() {
        return securityRepository.findAllUserName();
    }

    @Override
    public Set<String> getEmail() {
        return securityRepository.finalAllEmail();
    }
}
