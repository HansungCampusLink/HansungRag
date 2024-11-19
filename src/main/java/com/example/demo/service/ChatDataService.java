package com.example.demo.service;

import com.example.demo.dto.ChatDataDto;
import com.example.demo.dto.MessageDto;
import com.example.demo.entity.ChatData;
import com.example.demo.repository.ChatDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatDataService {

    private final ChatDataRepository chatDataRepository;

    public List<MessageDto> getChatHistory(Long id) {
        return getChatDataById(id).getMessages()
                .stream()
                .map(MessageDto::fromEntity)
                .toList();
    }

    public void addMessage(Long id, MessageDto ...message) {
        ChatData chatData = getChatDataById(id);
        Arrays.stream(message).map(MessageDto::toEntity).forEach(chatData.getMessages()::add);
        chatDataRepository.save(chatData);
    }

    private ChatData getChatDataById(Long id) {
        return chatDataRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Chat data not found"));
    }

    public Long saveChatData(ChatDataDto chatData, MessageDto ...additionalMessages) {
        ChatData chatDataEntity = new ChatData();
        List<ChatData.Message> messages = new ArrayList<>();
        chatData.getMessages().stream().map(MessageDto::toEntity).forEach(messages::add);
        Arrays.stream(additionalMessages).map(MessageDto::toEntity).forEach(messages::add);
        chatDataEntity.setMessages(messages);
        return chatDataRepository.save(chatDataEntity).getId();
    }

}
