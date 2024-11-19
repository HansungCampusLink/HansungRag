package com.example.demo.controller;

import com.example.demo.dto.ChatDataDto;

import com.example.demo.dto.MessageDto;
import com.example.demo.dto.RagChatDto;
import com.example.demo.service.ChatDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class RagChatController {
    private final ChatClient chatClient;
    private final ChatDataService chatDataService;

    @PostMapping
    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {

        List<MessageDto> messages = chatDataDto.getMessages();

        // 마지막 메시지의 content 가져오기
        MessageDto lastMessage = messages.get(messages.size() - 1);
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

        List<Document> documents = chatResponse.getMetadata().get(RETRIEVED_DOCUMENTS);

        List<String> refLists = documents.stream()
                .map(Document::getMetadata)
                .map(metadata -> metadata.get("link").toString())
                .collect(Collectors.toList());


        String content = chatResponse.getResult().getOutput().getContent();
        MessageDto assistantMsg = new MessageDto("assistant", content, refLists);

        Long chatID = chatDataDto.getChatId();
        if (chatDataDto.getChatId() == null) {
            chatID = chatDataService.saveChatData(chatDataDto, assistantMsg);
        }
        else {
            chatDataService.addMessage(chatID, lastMessage, assistantMsg);
        }

        RagChatDto ragChatDto = new RagChatDto(chatID, assistantMsg, refLists);


        return ResponseEntity.ok(ragChatDto);
    }
}