package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RagChatDto {
    private Long chatId;
    private MessageDto messages;
    private List<String> ref;
    private String destination;

    public RagChatDto(Long chatId, MessageDto messages, List<String> ref, String destination) {
        this.chatId = chatId;
        this.messages = messages;
        this.ref = ref;
        this.destination = destination;
    }
}
