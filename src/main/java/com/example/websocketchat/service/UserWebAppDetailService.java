package com.example.websocketchat.service;

import com.example.websocketchat.model.UserModel;
import com.example.websocketchat.repository.UserRepository;
import com.example.websocketchat.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWebAppDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserWebAppDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userWebAppOptional = Optional.ofNullable(userRepository.findByUsername(username));

        //TODO Переделать костыль
        if(userWebAppOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserDetails(userWebAppOptional.get());
    }
}
