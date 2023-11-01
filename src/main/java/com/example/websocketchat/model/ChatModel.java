package com.example.websocketchat.model;

import com.example.websocketchat.chat.MessageType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "message_history")
public class ChatModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender", columnDefinition = "TEXT")
    private String sender;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "TEXT")
    private MessageType type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private Date timestamp;

    public ChatModel(String sender, String content, MessageType type) {
        this.sender = sender;
        this.type = type;
        this.timestamp = new Date();
        this.content = content;
    }

    public ChatModel() {
    }
}
