package com.example.demo.service;

import com.example.demo.dto.ChatDataDto;
import com.example.demo.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS;

@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatClient ragChatClient;

    public RagOutputDto getRagOutPut(ChatDataDto chatDataDto) {

        List<MessageDto> messages = chatDataDto.getMessages();

        MessageDto lastMessage = messages.get(messages.size() - 1);

        String userContent = String.format("who : %s, major: %s, question : ", chatDataDto.getWho(), chatDataDto.getMajor(), lastMessage.getContent());

        List<Message> chatHistory = chatDataDto.getMessages().stream()
                .map(message ->
                        switch (message.getRole()) {
                            case "user" -> new UserMessage(message.getContent());
                            case "assistant" -> new AssistantMessage(message.getContent());
                            default -> throw new IllegalStateException("Unexpected value: " + message.getRole());
                        })
                .collect(Collectors.toList());

        ChatResponse chatResponse = this.ragChatClient
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

        return new RagOutputDto(content, refLists);
    }

    public static record RagOutputDto(String content, List<String> refLists) {}
}
