package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class HistoryDto {
    private final List<MessageDto> messages;

    public HistoryDto(List<MessageDto> messages) {
        this.messages = messages;
    }
}
