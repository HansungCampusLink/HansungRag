package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String role;
    private String content;

    public MessageDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}