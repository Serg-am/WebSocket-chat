package com.example.websocketchat.controller;

import com.example.websocketchat.chat.ChatMessage;
import com.example.websocketchat.model.ChatModel;
import com.example.websocketchat.chat.MessageType;
import com.example.websocketchat.repository.ChatMessageRepository;
import com.example.websocketchat.security.ChatModelEncryptionService;
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
    private final ChatModelEncryptionService chatModelEncryptionService;

    public ChatController(ChatMessageRepository chatMessageRepository, ChatModelEncryptionService chatModelEncryptionService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatModelEncryptionService = chatModelEncryptionService;
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
        ChatModel chatModel = new ChatModel(chatMessage.getSender(), chatModelEncryptionService.encryptContent(chatMessage.getContent()), MessageType.CHAT);
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
        List<ChatModel> chatHistory = chatMessageRepository.findByOrderByTimestampAsc();

        // Расшифровать контент каждого сообщения перед возвратом
        chatHistory.forEach(chatModel -> {
            String decryptedContent = chatModelEncryptionService.decryptContent(chatModel.getContent());
            chatModel.setContent(decryptedContent);
        });

        return chatHistory;
    }
}
