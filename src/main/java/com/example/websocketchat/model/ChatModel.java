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

    @Enumerated(EnumType.STRING) // Чтобы сохранять enum как строку
    @Column(name = "type", columnDefinition = "TEXT")
    private MessageType type;


    @Temporal(TemporalType.TIMESTAMP) // Указываем, что это поле для хранения даты и времени
    @Column(name = "timestamp")
    private Date timestamp; // Поле timestamp

    public ChatModel(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.timestamp = new Date(); // Установите текущее время
    }

    public ChatModel() {

    }
}

