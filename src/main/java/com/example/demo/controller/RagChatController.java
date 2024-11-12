package com.example.demo.controller;

import com.example.demo.dto.ChatDataDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class RagChatController {

    @PostMapping
    public ResponseEntity<Map> chat(@RequestBody ChatDataDto chatDataDto) {
        List<ChatDataDto.Message> messages = chatDataDto.getMessages();

        if (messages != null && !messages.isEmpty()) {
            // 마지막 메시지의 content 가져오기
            ChatDataDto.Message lastMessage = messages.get(messages.size() - 1);
            String userContent = lastMessage.getContent();

            // 응답 구성
            Map<String, Object> response = Map.of(
                    "messages", Map.of(
                            "role", "assistant",
                            "content", "I'm Good! " + userContent
                    ),
                    "ref", List.of(
                            "www.hansung/article/123",
                            "https://www.hansung.ac.kr/bbs/hansung/143/264590/artclView.do?layout=unknown"
                    )
            );

            return ResponseEntity.ok(response);
        } else {
            // 오류 메시지 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request format. 'messages' must be a non-empty array."));
        }
    }
}
