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

    public RagChatDto(Long chatId, MessageDto messages, List<String> ref) {
        this.chatId = chatId;
        this.messages = messages;
        this.ref = ref;
    }
}
