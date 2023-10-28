package com.example.websocketchat.repository;

import com.example.websocketchat.model.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatModel, Long> {
    List<ChatModel> findByOrderByTimestampAsc();
}

