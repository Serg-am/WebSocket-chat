package com.example.websocketchat.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueKeyGenerator {

    public String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }
}
