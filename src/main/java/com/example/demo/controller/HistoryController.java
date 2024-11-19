package com.example.demo.controller;

import com.example.demo.dto.HistoryDto;
import com.example.demo.service.ChatDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final ChatDataService chatDataService;

    @GetMapping("/{chatId}")
    public HistoryDto getHistory(@PathVariable(required = true) Long chatId) {
        return new HistoryDto(chatDataService.getChatHistory(chatId));
    }
}
