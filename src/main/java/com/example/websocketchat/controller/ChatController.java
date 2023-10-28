package com.example.websocketchat.controller;

import com.example.websocketchat.chat.ChatMessage;
import com.example.websocketchat.model.ChatModel; // Импортируйте ваш класс ChatModel
import com.example.websocketchat.chat.MessageType;
import com.example.websocketchat.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {


    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public String getUser(Principal principal) {
        if (principal == null) {
            return "";
        }
        return principal.getName();
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        // Сохранить сообщение в базе данных
        ChatModel chatModel = new ChatModel(chatMessage.getSender(), chatMessage.getContent(), MessageType.CHAT);
        chatMessageRepository.save(chatModel);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/getChatHistory")
    @ResponseBody
    public List<ChatModel> getChatHistory() {
        // Получить историю чата из базы данных
        return chatMessageRepository.findByOrderByTimestampAsc();
    }
}
