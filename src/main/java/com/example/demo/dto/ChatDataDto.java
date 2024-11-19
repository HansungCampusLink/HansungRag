package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDataDto {
    private String who = "unknown";
    private String major = "unKnown";
    private List<Message> messages;

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;
    }
}