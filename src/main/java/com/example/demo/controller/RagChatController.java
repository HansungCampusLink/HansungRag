package com.example.demo.controller;

import com.example.demo.dto.ChatDataDto;

import com.example.demo.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class RagChatController {
    private final ChatClient chatClient;
    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<Map> chat(@RequestBody ChatDataDto chatDataDto) {

        List<ChatDataDto.Message> messages = chatDataDto.getMessages();

        if (messages != null && !messages.isEmpty()) {
            // 마지막 메시지의 content 가져오기
            ChatDataDto.Message lastMessage = messages.get(messages.size() - 1);
            String userContent = lastMessage.getContent();

            List<Message> chatHistory = chatDataDto.getMessages().stream()
                    .map(message ->
                            switch (message.getRole()) {
                                case "user" -> new UserMessage(message.getContent());
                                case "assistant" -> new AssistantMessage(message.getContent());
                                default -> throw new IllegalStateException("Unexpected value: " + message.getRole());
                            })
                    .collect(Collectors.toList());

            ChatResponse chatResponse = this.chatClient
                    .prompt()
                    .messages(chatHistory)
                    .user(userContent)
                    .call().chatResponse();

            String content = chatResponse.getResult().getOutput().getContent();
//            System.out.println(chatResponse);

            List<String> refLists = documentService.findSimilarDocuments(userContent);


            // 응답 구성
            Map<String, Object> response = Map.of(
                    "messages", Map.of(
                            "role", "assistant",
                            "content", content
                    ),
                    "ref", refLists
            );

            return ResponseEntity.ok(response);
        } else {
            // 오류 메시지 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request format. 'messages' must be a non-empty array."));
        }
    }
}