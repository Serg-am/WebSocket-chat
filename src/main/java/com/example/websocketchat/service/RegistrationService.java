package com.example.websocketchat.service;

import com.example.websocketchat.model.UserModel;
import com.example.websocketchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserModel userModel){
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setRole("ROLE_USER");
        userModel.setActive(false);
        usersRepository.save(userModel);
    }
}

