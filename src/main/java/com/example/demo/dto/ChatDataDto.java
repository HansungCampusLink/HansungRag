package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDataDto {
    private Long chatId;
    private String who;
    private String major;
    @NotNull @NotEmpty
    private List<MessageDto> messages;
}